package rental.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.domain.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    public List<Room> findByAddressContaining(String addr);
}
