package org.example.devkorchat.auth.join;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JoinDTO {
    private String username;
    private String password;
    private String nickname;
    private String profileURL;

}

