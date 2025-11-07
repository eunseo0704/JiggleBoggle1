package com.spring.jiggleboggle1.service;

import com.spring.jiggleboggle1.domain.CodeVO;
import com.spring.jiggleboggle1.mapper.CodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeMapper codeMapper;

    public List<CodeVO> codeList(String codeHead) {
        return  codeMapper.codeList(codeHead);
    }


}
