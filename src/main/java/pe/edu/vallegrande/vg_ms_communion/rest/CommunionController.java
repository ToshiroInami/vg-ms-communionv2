package pe.edu.vallegrande.vg_ms_communion.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.vallegrande.vg_ms_communion.dto.CommunionAdminDto;
import pe.edu.vallegrande.vg_ms_communion.dto.CommunionUserDto;
import pe.edu.vallegrande.vg_ms_communion.model.Communion;
import pe.edu.vallegrande.vg_ms_communion.service.impl.CommunionAdminServiceImp;
import pe.edu.vallegrande.vg_ms_communion.service.impl.CommunionUserServiceImp;
import pe.edu.vallegrande.vg_ms_communion.service.CommunionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/communions")
public class CommunionController {

    //COMMUNION (creo que no es necesario)
//    @Autowired
//    private CommunionService communionService;
//
//    //Listar todos desde communions
//    @GetMapping
//    public Flux<Communion> getAllCommunions() {
//        return communionService.getAllCommunions();
//    }
//
//    //Listar por id todos desde communions
//    @GetMapping("/{id}")
//    public Mono<ResponseEntity<Communion>> getCommunionById(@PathVariable String id) {
//        return communionService.getCommunionById(id)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
//    //Crear desde communions
////    @PostMapping
////    public Mono<Communion> createCommunion(@RequestBody Communion communion) {
////        return communionService.createCommunion(communion);
////    }
//
//    //Eliminar desde communions
//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<Void>> deleteCommunion(@PathVariable String id) {
//        return communionService.deleteCommunion(id)
//                .then(Mono.just(ResponseEntity.ok().<Void>build()))
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
//    //Actualizar por completo desde communion
//    @PutMapping("/{id}")
//    public Mono<ResponseEntity<Communion>> updateCommunion(@PathVariable String id, @RequestBody Communion communion) {
//        return communionService.updateCommunion(id, communion)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }

    //USER
//    @Autowired
//    private CommunionUserServiceImp userService;

//    //Crear desde la vista USUARIO
//    @PostMapping("/user")
//    public Mono<Communion> createCommunionFromUserDto(@RequestBody CommunionUserDto userDto, MultipartFile[] files) {
//        return userService.createCommunionFromUserDto(userDto);
//    }

    private final CommunionAdminServiceImp comunionService;
    private final CommunionUserServiceImp userComunionService;

    @Autowired
    public CommunionController(CommunionAdminServiceImp comunionService, CommunionUserServiceImp userComunionService) {
        this.comunionService = comunionService;
        this.userComunionService = userComunionService;
    }

    @PostMapping("/user")
    public Mono<ResponseEntity<Communion>> createCommunionFromUserDto(@ModelAttribute CommunionUserDto communionUserDto,
                                                                      @RequestParam("files") MultipartFile[] files) {
        return userComunionService.createCommunionFromUserDto(communionUserDto, files)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }


    //ADMIN
    @Autowired
    private CommunionAdminServiceImp adminService;

    //Listar todos desde admin
    @GetMapping("/total/admin")
    public Flux<Communion> getTotalCommunionsAsAdminDto() {
        return adminService.getAllCommunions();
    }

    //Listar APROBADOS desde admin
    @GetMapping("/approved/admin")
    public Flux<Communion> getApprovedCommunionsAsAdminDto() {
        return adminService.getApprovedCommunions();
    }

    //Listar RECHAZADOS desde admin
    @GetMapping("/rejected/admin")
    public Flux<Communion> getRejectedCommunionsAsAdminDto() {
        return adminService.getRejectedCommunions();
    }

    //Listar PENDIENTES desde admin
    @GetMapping("/pending/admin")
    public Flux<Communion> getPendingCommunionsAsAdminDto() {
        return adminService.getPendingCommunions();
    }

    //Listar por ID desde admin
    @GetMapping("/admin/{id}")
    public Mono<ResponseEntity<Communion>> getCommunionByIdAsAdminDto(@PathVariable String id) {
        return adminService.getCommunionById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //Actualizar por ID al User Creado
    @PatchMapping("/admin/{id}")
    public Mono<ResponseEntity<Communion>> patchCommunionFromAdminDto(@PathVariable String id, @RequestBody CommunionAdminDto adminDto) {
        return adminService.patchCommunion(id, adminDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
