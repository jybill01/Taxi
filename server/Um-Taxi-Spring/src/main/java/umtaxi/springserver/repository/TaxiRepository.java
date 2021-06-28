package umtaxi.springserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umtaxi.springserver.model.TaxiEntity;

@Repository
public interface TaxiRepository extends JpaRepository<TaxiEntity,Long> {
}
