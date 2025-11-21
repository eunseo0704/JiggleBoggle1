package com.spring.jiggleboggle1.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jiggleboggle1.domain.ChatMessage;
import com.spring.jiggleboggle1.security.CookieUtil;
import com.spring.jiggleboggle1.security.JwtUtil;
import com.spring.jiggleboggle1.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;

/*****
 ***       | 기능                  | 설명                             |
 ***       | ------------------- | ------------------------------ |
 ***       | `/api/chat/history` | 기존 채팅 가져오기                         |
 ***       | `/api/chat/send`    | 사용자가 메시지 보낼 때 호출                 |
 ***       | `/api/chat/stream`  | AI가 조각 단위로 답변을 보냄(stream, SSE)   |
 ***       | `SseEmitter`        | 프론트에 실시간 스트림 전달                  |
 ***       | 전체 메시지 완성되면     | DB에 저장                               |
 ***/

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ObjectMapper om = new ObjectMapper();
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;

    // 페이지
    @GetMapping("/chat")
    public String chatPage(Model model, HttpServletRequest request) {
        // 실제 앱이면 로그인한 userId를 세션/JWT에서 넣으세요
        String userId ="";
        String token = cookieUtil.getTokenFromCookies(request, "JWT_TOKEN");
        if(token != null) {
            userId = jwtUtil.getUserIdFromToken(token);
        }


        model.addAttribute("userId", 1);
        return "recipe/chat";
    }

    // 히스토리 API
    @GetMapping("/api/chat/history")
    @ResponseBody
    public List<ChatMessage> history(@RequestParam Long userId){
        return chatService.getHistory(userId);
    }

    // 사용자 메시지 저장 (클라이언트가 먼저 호출)
    @PostMapping("/api/chat/send")
    @ResponseBody
    public Map<String,Object> send(@RequestBody Map<String,Object> body){
        Long userId = Long.parseLong(body.get("userId").toString());
        String message = body.get("message").toString();

        ChatMessage saved = chatService.saveUserMessage(userId, message);
        return Map.of("messageId", saved.getId());
    }

    // SSE 스트리밍 엔드포인트: OpenAI 스트림을 읽어 조각별로 클라이언트에 보냄
    @GetMapping(path = "/api/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestParam Long userId) {
        SseEmitter emitter = new SseEmitter(0L); // no timeout
        Executors.newSingleThreadExecutor().submit(() -> {
            StringBuilder assistantFull = new StringBuilder();
            try {
                // build messages array (history)
                List<JsonNode> messages = chatService.buildMessagesForOpenAi(userId);
                // optionally add a system prompt to guide responses
                JsonNode systemNode = om.createObjectNode().put("role","system")
                        .put("content","당신은 친절한 요리 어시스턴트입니다. 레시피를 추천하고, 재료와 조리법 요약을 제공하세요.");
                messages.add(0, systemNode);

                String payload = chatService.createPayloadForStreaming(messages);
                InputStream is = chatService.streamFromOpenAi(payload);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.isBlank()) continue;
                        // OpenAI streaming lines are like: data: { ... } or data: [DONE]
                        if (line.startsWith("data: ")) line = line.substring(6);
                        if ("[DONE]".equals(line.trim())) {
                            emitter.send(SseEmitter.event().name("end").data("[DONE]"));
                            break;
                        }
                        // parse JSON chunk
                        JsonNode chunk = chatService.parseChunk(line.trim());
                        if (chunk == null) continue;
                        JsonNode choices = chunk.path("choices");
                        if (choices.isArray() && choices.size() > 0) {
                            JsonNode delta = choices.get(0).path("delta");
                            JsonNode content = delta.path("content");
                            if (!content.isMissingNode()) {
                                String part = content.asText();
                                assistantFull.append(part);
                                emitter.send(SseEmitter.event().name("message").data(part));
                            }
                        }
                    }
                }

                // save assistant full message to DB
                chatService.saveAssistantMessage(userId, assistantFull.toString());

                emitter.complete();
            } catch (Exception e) {
                try { emitter.send(SseEmitter.event().name("error").data(e.getMessage())); } catch (Exception ignored){}
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
