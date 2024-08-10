package Auth.Join;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/auth")
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }

    @PostMapping("/join")
    @ResponseBody
    public String joinProcess(JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);

        return "Join success";
    }
}
