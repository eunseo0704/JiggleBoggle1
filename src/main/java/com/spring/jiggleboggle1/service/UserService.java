package com.spring.jiggleboggle1.service;


import com.spring.jiggleboggle1.mapper.UserMapper;
import com.spring.jiggleboggle1.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int setSignUp(UserVO userVo) {

        return userMapper.setSignUp(userVo);
    }
}
