package User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_account")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    //role: user, admin
    private String role;

    public UserEntity(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


}
