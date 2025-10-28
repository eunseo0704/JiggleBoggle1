package com.spring.jiggleboggle1.service;


import com.spring.jiggleboggle1.mapper.UserMapper;
import com.spring.jiggleboggle1.domain.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public int setSignUp(UserVO userVo) {

        try {
            String encodedPassword = passwordEncoder.encode(userVo.getUserPswd());
            userVo.setUserPswd(encodedPassword);

             return userMapper.setSignUp(userVo);

    } catch (Exception e) {
            log.error("회원가입 중 오류 발생: {}", e.getMessage(), e);
            return -1; // 예외 발생시 -1
        }
    }

    public UserVO loginUser(String userId, String userPswd) {

        UserVO userVo = userMapper.findByUserId(userId);

        if (userVo != null && passwordEncoder.matches(userPswd, userVo.getUserPswd())) {
            return userVo;
        }
        return null;
    }




}
