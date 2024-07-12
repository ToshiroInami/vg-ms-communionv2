package pe.edu.vallegrande.vg_ms_communion.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_communion.dto.CommunionAdminDto;
import pe.edu.vallegrande.vg_ms_communion.model.Communion;
import pe.edu.vallegrande.vg_ms_communion.repository.CommunionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CommunionAdminServiceImp {

    @Autowired
    private CommunionRepository communionRepository;

    //Listar TODOS desde ADMIN
    public Flux<Communion> getAllCommunions() {
        return communionRepository.findAll();
    }

    //Listar APROBADOS desde ADMIN
    public Flux<Communion> getApprovedCommunions() {
        return communionRepository.findByState('A');
    }


    //Listar PENDIENTES desde ADMIN
    public Flux<Communion> getPendingCommunions() {
        return communionRepository.findByState('P');
    }

    //Listar RECHAZADAS desde ADMIN
    public Flux<Communion> getRejectedCommunions() {
        return communionRepository.findByState('R');
    }

    //Listar por ID desde ADMIN
    public Mono<Communion> getCommunionById(String id) {
        return communionRepository.findById(id);
    }

    // Actualizar por el ID de User creado desde el ADMIN
    public Mono<Communion> patchCommunion(String id, CommunionAdminDto adminDto) {
        return communionRepository.findById(id)
                .flatMap(existingCommunion -> {
                    if (adminDto.getNames() != null) {
                        existingCommunion.setNames(adminDto.getNames());
                    }
                    if (adminDto.getSurnames() != null) {
                        existingCommunion.setSurnames(adminDto.getSurnames());
                    }
                    if (adminDto.getPriest() != null) {
                        existingCommunion.setPriest(adminDto.getPriest());
                    }
                    if (adminDto.getPlaceCommunion() != null) {
                        existingCommunion.setPlaceCommunion(adminDto.getPlaceCommunion());
                    }
                    if (adminDto.isEnrolledCatechesis()) {
                        existingCommunion.setEnrolledCatechesis(adminDto.isEnrolledCatechesis());
                    }
                    if (adminDto.getComment() != null) {
                        existingCommunion.setComment(adminDto.getComment());
                    }
                    if (existingCommunion.getState() == 'P') {
                        existingCommunion.setState(adminDto.getState());
                    }
                    if (adminDto.getCommunionDate() != null) {
                        existingCommunion.setCommunionDate(adminDto.getCommunionDate());
                    }
                    return communionRepository.save(existingCommunion);
                });
    }
}
