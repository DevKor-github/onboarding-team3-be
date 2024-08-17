package org.example.devkorchat.auth.join;


import org.example.devkorchat.user.UserEntity;
import org.example.devkorchat.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean isValidUsername(String username){
        if(username == null || username.isEmpty()){
            return false;
        }

        //username: 5-20자, 영어대소문자와 _+&*- 포함 가능, 중간에 . 포함 가능
        String usernameRegex = "^(?=.{5,20}$)[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();

    }

    public boolean isValidPassword(String password){
        if(password == null || password.isEmpty()){
            return false;
        }
        if(password.length() < 8) {
            return false;
        }
        
        //password: 8자 이상, 영어 대문자, 소문자, 숫자, 특수기호 필수로 포함
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void joinProcess(JoinDTO joinDTO){


        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String role = "general"; //set role: general by default

        if(!isValidUsername(username)){
            throw new IllegalArgumentException("Invalid username format");
        }
        if(!isValidPassword(password)){
            throw new IllegalArgumentException("Invalid password format");
        }

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist) return;

        UserEntity newUser = new UserEntity(username,bCryptPasswordEncoder.encode(password), role);

        userRepository.save(newUser);
        System.out.println("join complete");

    }

}
