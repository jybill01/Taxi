package umtaxi.springserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import umtaxi.springserver.model.MatchEntity;
import umtaxi.springserver.repository.MatchRepository;

import java.util.Optional;

public class MatchService {
    private MatchRepository matchRepository;

    @Autowired
    MatchService(MatchRepository matchRepository) { this.matchRepository = matchRepository; }


    public void addMatch(MatchEntity matchEntity){
        matchRepository.save(matchEntity);
    }

    public MatchEntity getMatch(long id){
        Optional<MatchEntity> match = matchRepository.findMatchEntitiesByTaxiId(id);
        if(!match.isPresent()) return null;
        return match.get();
    }


}
