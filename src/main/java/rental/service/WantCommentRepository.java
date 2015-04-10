package rental.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.domain.WantComment;

public interface WantCommentRepository extends JpaRepository<WantComment, Long> {
}
