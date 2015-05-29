package rental.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rental.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    public Page<Room> findByAddressContaining(String addr, Pageable pageable);
}
