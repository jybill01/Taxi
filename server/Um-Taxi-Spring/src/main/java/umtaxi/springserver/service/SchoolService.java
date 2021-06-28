package umtaxi.springserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umtaxi.springserver.model.SchoolEntity;
import umtaxi.springserver.repository.SchoolRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SchoolService {
    private SchoolRepository schoolRepository;
    @Autowired
    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public ArrayList<SchoolEntity> findSchool(){
        ArrayList<SchoolEntity> schoolList = new ArrayList<>(schoolRepository.findAll());
        return schoolList;
    }

    public void addSchool (SchoolEntity school){
        schoolRepository.save(school);

    }

    public SchoolEntity getSchool (long id ){
        Optional<SchoolEntity> school = schoolRepository.findById(id);
        if (school.isEmpty()) {
            return null;
        }
        return school.get();
    }
}
