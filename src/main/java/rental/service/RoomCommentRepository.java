package rental.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.domain.RoomComment;

public interface RoomCommentRepository extends JpaRepository<RoomComment, Long> {
}
