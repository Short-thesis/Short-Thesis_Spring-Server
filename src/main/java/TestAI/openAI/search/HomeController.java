package TestAI.openAI.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor HomeController {

    @GetMapping("/")
    public String home(){
        return "search";
    }
}
