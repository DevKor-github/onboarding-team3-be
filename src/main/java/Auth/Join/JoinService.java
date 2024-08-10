package Auth.Join;


import User.UserEntity;
import User.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;

    public JoinService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void joinProcess(JoinDTO joinDTO){


        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String role = "general"; //set role: general by default

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist) return;

        UserEntity newUser = new UserEntity(username, password, role);

        userRepository.save(newUser);

    }

}
