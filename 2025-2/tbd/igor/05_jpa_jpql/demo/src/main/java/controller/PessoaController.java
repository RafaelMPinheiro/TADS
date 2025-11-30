package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@Controller
public class PessoaController {

    @GetMapping("/")
    public String requestMethodName() {
        return new String("oi");
    }
    
    
}
