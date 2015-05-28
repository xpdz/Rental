package rental.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import rental.service.AccountRepository;
import rental.service.RepositoryUserDetailService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties security;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll()
                .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and().logout().permitAll()
                .and().rememberMe()
                .and().apply(new SpringSocialConfigurer())
                .and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder(new BCryptPasswordEncoder(8));
        auth.userDetailsService(new RepositoryUserDetailService(accountRepository));
    }

    @Bean
    public SocialUserDetailsService socialUsersDetailService() {
        return new RepositoryUserDetailService(accountRepository);
    }

    @Bean
    @Scope(value="singleton", proxyMode= ScopedProxyMode.INTERFACES)
    public UsersConnectionRepository usersConnectionRepository(AccountRepository accountRepository) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
                dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setConnectionSignUp(new AccountConnectionSignUp(accountRepository));
        return repository;
    }
}
