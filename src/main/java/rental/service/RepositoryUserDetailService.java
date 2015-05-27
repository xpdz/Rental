package rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import rental.domain.Account;

public class RepositoryUserDetailService implements UserDetailsService, SocialUserDetailsService {
    AccountRepository accountRepository;

    @Autowired
    public RepositoryUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        Account principal = Account.getBuilder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .enabled(account.isEnabled())
                .role(account.getRole())
                .socialSignInProvider(account.getSocialSignInProvider())
                .build();
        return principal;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        UserDetails userDetails = accountRepository.findByUsername(userId);
        return (SocialUserDetails) userDetails;
    }
}
