package org.example.devkorchat.auth.join;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@Controller
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }
    @PostMapping("/join")
    @ResponseBody
    public String joinProcess(@RequestBody JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);

        return "Join success";
    }
}
