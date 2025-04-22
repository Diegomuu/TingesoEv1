package Diego.KartingBack.repositories;

import Diego.KartingBack.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
}
