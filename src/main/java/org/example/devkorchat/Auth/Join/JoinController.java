package org.example.devkorchat.Auth.Join;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }


    @GetMapping("/test")
    @ResponseBody
    public String test(){
        System.out.println("test");
        return "test page";
    }
    @PostMapping("/join")
    @ResponseBody
    public String joinProcess(@RequestBody JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);

        return "Join success";
    }
}
