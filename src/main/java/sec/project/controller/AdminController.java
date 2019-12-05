
package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
    
    @ResponseBody
    @GetMapping("/adminpath")
    public String adminpath() {
        return "This is very very secret stuff, that only admins should be able to see!";
    }
    
    
}
