package umtaxi.springserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umtaxi.springserver.model.MatchEntity;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity,Long> {
    Optional<MatchEntity> findMatchEntitiesByTaxiId(long taxiiId);
}
