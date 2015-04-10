package rental.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
