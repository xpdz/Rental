package rental.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
