package org.example.devkorchat.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;
    @Column(name = "PROFILE_URL", nullable = false)
    private String profileURL;

    //role: user, admin
    private String role;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public UserEntity(String username, String password, String nickname, String profileURL, String role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.profileURL = profileURL;
        this.role = role;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }

}
