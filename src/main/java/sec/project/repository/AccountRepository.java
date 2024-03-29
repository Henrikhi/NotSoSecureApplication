package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findByName(String name);

    
}
