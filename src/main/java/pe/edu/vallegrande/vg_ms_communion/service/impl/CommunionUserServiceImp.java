package pe.edu.vallegrande.vg_ms_communion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.vallegrande.vg_ms_communion.dto.CommunionUserDto;
import pe.edu.vallegrande.vg_ms_communion.dto.StorageResponseDto;
import pe.edu.vallegrande.vg_ms_communion.feignclient.StorageFeignClient;
import pe.edu.vallegrande.vg_ms_communion.model.Communion;
import pe.edu.vallegrande.vg_ms_communion.repository.CommunionRepository;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class CommunionUserServiceImp {

    private final CommunionRepository communionRepository;
    private final StorageFeignClient storageFeignClient;
    private final ObjectMapper mapper;

    private Mono<List<String>> extractFileUrls(String responseBody) {
        return Mono.fromCallable(() -> mapper.readValue(responseBody, StorageResponseDto.class).getFilesUrl());
    }

    public Mono<ResponseEntity<Communion>> createCommunionFromUserDto(CommunionUserDto userDto, MultipartFile[] files) {
        // Crear comunión desde DTO de usuario
        Communion communion = new Communion();
        communion.setId(UUID.randomUUID().toString());
        communion.setPersonId(userDto.getPersonId());
        communion.setNames(userDto.getNames());
        communion.setSurnames(userDto.getSurnames());
        communion.setPlaceCommunion(userDto.getPlaceCommunion());
        communion.setEnrolledCatechesis(userDto.isEnrolledCatechesis());
        communion.setState('P');
        communion.setCommunionDate(null);

        return Mono.fromCallable(() -> storageFeignClient.uploadFile(files, "communion", communion.getPersonId(), communion.getId()))
                .flatMap(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful() ?
                        extractFileUrls(responseEntity.getBody()) :
                        Mono.error(new RuntimeException("No se pudieron cargar archivos al servicio de almacenamiento")))
                .doOnNext(communion::setStorageId)
                .flatMap(urls -> communionRepository.save(communion))
                .map(savedBaptism -> new ResponseEntity<>(savedBaptism, HttpStatus.CREATED))
                .onErrorReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
