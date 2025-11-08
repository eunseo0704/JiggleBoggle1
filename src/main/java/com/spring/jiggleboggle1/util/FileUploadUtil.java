package com.spring.jiggleboggle1.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class FileUploadUtil {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    /**
     * 파일 업로드 (에러 내부 처리)
     * @param files 업로드할 파일들
     * @param baseUploadDir 기본 저장 경로 (예: "uploads")
     * @param recipeId 레시피 고유 ID
     * @return 업로드된 파일의 상대 경로 리스트 (에러 시 빈 리스트 반환)
     */
    public static List<String> saveRecipeFiles(List<MultipartFile> files, String baseUploadDir, Long recipeId) {
        List<String> filePaths = new ArrayList<>();

        try {
            // 날짜별 + 레시피ID 폴더 생성
            String today = LocalDate.now().toString();
            String folderName = today + "_" + recipeId;
            Path uploadPath = Paths.get(baseUploadDir, folderName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {

                    // ✅ 크기 제한 (5MB 초과 시 중단)
                    if (file.getSize() > MAX_FILE_SIZE) {
                        System.err.println("[FileUploadUtil] 파일 크기 초과: " + file.getOriginalFilename());
                        return Collections.emptyList();
                    }

                    // ✅ 파일명 지정
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);

                    // ✅ 파일 저장
                    file.transferTo(filePath.toFile());

                    // ✅ 웹 접근 가능한 상대경로 추가
                    filePaths.add("/uploads/" + folderName + "/" + fileName);
                }
            }

        } catch (IOException e) {
            System.err.println("[FileUploadUtil] 파일 업로드 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList(); // 실패 시 빈 리스트 반환
        }

        return filePaths;
    }
}
