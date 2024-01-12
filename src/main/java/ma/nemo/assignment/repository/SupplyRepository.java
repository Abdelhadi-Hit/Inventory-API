package ma.nemo.assignment.repository;

import ma.nemo.assignment.domain.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findByExpirationDateBetween(LocalDateTime start, LocalDateTime end);
}
