package bio.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class SampleController {
    @GetMapping("/hello") //url이 /hello로 끝나면 해당 메소드가 실행
    public void hello(Model model){
        log.info("hello.......................");

        model.addAttribute("msg", "Hello World");
    }
}
