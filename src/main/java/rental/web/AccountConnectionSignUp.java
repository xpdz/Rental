package rental.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import rental.domain.Account;
import rental.service.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountConnectionSignUp implements ConnectionSignUp {
    private final AccountRepository accountRepository;

    public AccountConnectionSignUp(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_USER");
        List<GrantedAuthority> sgas = new ArrayList();
        sgas.add(sga);
        Account account = new Account(profile.getUsername(), "", sgas);
        accountRepository.save(account);
        return account.getUsername();
    }
}
