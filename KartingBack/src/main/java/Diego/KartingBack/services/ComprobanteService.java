package Diego.KartingBack.services;

import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.repositories.ComprobanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    public void guardarComprobante(ComprobanteEntity comprobante) {
        comprobanteRepository.save(comprobante);
    }
}
