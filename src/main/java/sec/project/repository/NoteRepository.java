package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Account;
import sec.project.domain.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser(Account user);

}
