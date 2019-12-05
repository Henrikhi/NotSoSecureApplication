package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class SignupController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/newAccount")
    public String newAccount() {
        return "newAccount";
    }

    @PostMapping("/newAccount")
    public String createNewAccount(@RequestParam String name, @RequestParam String password) {

        if (name == null || password == null || name.trim().equals("") || password.trim().equals("")) {
            return "newAccount";
        }

        if (accountRepository.findByName(name) == null) {
            Account account = new Account();
            account.setName(name);
            account.setPassword(password);
            account.setAdmin(false);
            accountRepository.save(account);
            return "redirect:/";
        }
        return "newAccount";
    }

}
