package com.spring.jiggleboggle1.mapper;

import com.spring.jiggleboggle1.domain.CodeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {

    public List<CodeVO> codeList(String codeHead);


}
