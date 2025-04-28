package Diego.KartingBack.repositories;

import Diego.KartingBack.entities.ComprobanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComprobanteRepository extends JpaRepository<ComprobanteEntity, Long> {
    Optional<ComprobanteEntity> findByCodigoReserva(String codigoReserva);

}
