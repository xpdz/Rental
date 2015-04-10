package rental.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Base class for Room & Want
 */
@MappedSuperclass
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name="account_id")
    private Account account;
    private Date lastModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
