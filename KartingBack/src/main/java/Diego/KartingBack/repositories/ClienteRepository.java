package Diego.KartingBack.repositories;

import Diego.KartingBack.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository <ClienteEntity, Long> {

}
