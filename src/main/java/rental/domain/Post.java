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
    private long accountId;
    private Date lastModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
