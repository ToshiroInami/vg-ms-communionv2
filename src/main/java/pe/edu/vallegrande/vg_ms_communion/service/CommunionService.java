package pe.edu.vallegrande.vg_ms_communion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_communion.dto.CommunionAdminDto;
import pe.edu.vallegrande.vg_ms_communion.model.Communion;
import pe.edu.vallegrande.vg_ms_communion.repository.CommunionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class CommunionService {

    @Autowired
    private CommunionRepository communionRepository;

    public Flux<Communion> getAllCommunions() {
        return communionRepository.findAll();
    }

    public Mono<Communion> getCommunionById(String id) {
        return communionRepository.findById(id);
    }

    public Mono<Communion> createCommunion(Communion communion) {
        // Validaciones y lógica de negocio aquí, si es necesario
        return communionRepository.save(communion);
    }

    public Mono<Communion> updateCommunion(String id, Communion updatedCommunion) {
        return communionRepository.findById(id)
                .flatMap(existingCommunion -> {
                    // Actualizar campos relevantes
                    existingCommunion.setNames(updatedCommunion.getNames());
                    existingCommunion.setSurnames(updatedCommunion.getSurnames());
                    existingCommunion.setPlaceCommunion(updatedCommunion.getPlaceCommunion());
                    existingCommunion.setEnrolledCatechesis(updatedCommunion.isEnrolledCatechesis());
                    existingCommunion.setState(updatedCommunion.getState());
                    existingCommunion.setPriest(updatedCommunion.getPriest());
                    existingCommunion.setCommunionDate(updatedCommunion.getCommunionDate());
                    existingCommunion.setComment(updatedCommunion.getComment());
                    return communionRepository.save(existingCommunion);
                });
    }

    public Mono<Void> deleteCommunion(String id) {
        return communionRepository.deleteById(id);
    }
}
