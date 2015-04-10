package rental.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.domain.Want;

public interface WantRepository extends JpaRepository<Want, Long> {
}
