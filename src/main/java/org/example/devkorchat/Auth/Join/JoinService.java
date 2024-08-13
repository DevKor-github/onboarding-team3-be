package org.example.devkorchat.Auth.Join;


import org.example.devkorchat.User.UserEntity;
import org.example.devkorchat.User.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO){


        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String role = "general"; //set role: general by default

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist) return;

        UserEntity newUser = new UserEntity(username,bCryptPasswordEncoder.encode(password), role);

        userRepository.save(newUser);
        System.out.println("join complete");

    }

}
