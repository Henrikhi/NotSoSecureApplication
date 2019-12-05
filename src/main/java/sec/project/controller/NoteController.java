package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sec.project.domain.Account;
import sec.project.domain.Note;
import sec.project.repository.AccountRepository;
import sec.project.repository.NoteRepository;

@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("*")
    public String view() {
        return "redirect:/";
    }

    @GetMapping("/")
    public String view(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByName(username);

        model.addAttribute("notes", noteRepository.findByUser(account));
        return "home";
    }

    @PostMapping("/")
    public String add(@RequestParam String content) {
        if (content != null && !content.trim().isEmpty()) {
            Note note = new Note();
            note.setContent(content.trim());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            note.setUser(accountRepository.findByName(username));
            noteRepository.save(note);
        }
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/notes/{id}")
    public String getNote(@PathVariable Long id) {

        Note note = noteRepository.getOne(id);

        String text = note.getUser().getName() + " says " + note.getContent();

        return text;
    }

}
