package com.spring.jiggleboggle1.mapper;

import com.spring.jiggleboggle1.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public int setSignUp(UserVO userVo);

    public UserVO findByUserId(String userId);

    public UserVO findByEmail(String userMail);

    public void insertUser(UserVO user);


}
