package sec.project.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.domain.Note;
import sec.project.repository.AccountRepository;
import sec.project.repository.NoteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NoteRepository noteRepository;

    @PostConstruct
    public void init() {

        if (accountRepository.findByName("admin") == null) {

            Account account = new Account();
            account.setName("admin");
            account.setPassword("admin");
            account.setAdmin(true);
            accountRepository.save(account);

            Note note = new Note();
            note.setContent("This is super secret note that only the original admin is supposed to see.");
            note.setUser(account);
            noteRepository.save(note);

        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByName(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        if (account.isAdmin()) {
            return new org.springframework.security.core.userdetails.User(
                    account.getName(),
                    account.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        } else {

            return new org.springframework.security.core.userdetails.User(
                    account.getName(),
                    account.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    Arrays.asList(new SimpleGrantedAuthority("USER")));
        }
    }
}
