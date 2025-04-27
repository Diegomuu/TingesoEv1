package Diego.KartingBack.repositories;

import Diego.KartingBack.entities.RackSemanalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RackSemanalRepository extends JpaRepository<RackSemanalEntity, Long> {
    Optional<RackSemanalEntity> findByFecha(LocalDate fecha);
}
