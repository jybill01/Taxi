package umtaxi.springserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umtaxi.springserver.model.SchoolEntity;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity,Long>{

}

