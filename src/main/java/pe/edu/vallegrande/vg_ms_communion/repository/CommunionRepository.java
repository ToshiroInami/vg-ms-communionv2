package pe.edu.vallegrande.vg_ms_communion.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vg_ms_communion.model.Communion;
import reactor.core.publisher.Flux;

@Repository
public interface CommunionRepository extends ReactiveMongoRepository<Communion, String> {

    Flux<Communion> findByState(char state);

}
