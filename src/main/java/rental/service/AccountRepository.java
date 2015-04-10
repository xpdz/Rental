package rental.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
