package rental.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Want extends Post implements Serializable {
    @OneToMany(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "want_id", referencedColumnName="id")
    private List<WantComment> wantComments = new ArrayList<>();

    public List<WantComment> getWantComments() {
        return wantComments;
    }

    public void setWantComments(List<WantComment> wantComments) {
        this.wantComments = wantComments;
    }
}
