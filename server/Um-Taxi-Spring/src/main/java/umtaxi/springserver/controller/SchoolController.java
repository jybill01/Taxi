package umtaxi.springserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import umtaxi.springserver.dto.SchoolDto;
import umtaxi.springserver.dto.SchoolListDto;
import umtaxi.springserver.model.SchoolEntity;
import umtaxi.springserver.service.SchoolService;

import java.util.ArrayList;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    private ArrayList<SchoolDto> schools;
    public SchoolController (){
        this.schools = new ArrayList<>();
        SchoolDto school1 = new SchoolDto();
        SchoolDto school2 = new SchoolDto();

        for(int i  = 0; i<=300; i++){
            SchoolDto dtoS = new SchoolDto();
            dtoS.id = i ;
            dtoS.name="학교 " + i;
            dtoS.totalStudent=i+100;
            dtoS.totalTeacher=10;
            this.schools.add(dtoS);
        }
    }

    private SchoolService schoolService;
    @Autowired
    public SchoolController(SchoolService schoolService){
        this.schoolService = schoolService;
    }


    @GetMapping("/{schoolId}")
    public SchoolDto getSchool(@PathVariable("schoolId") int schoolId){
        SchoolEntity entity = this.schoolService.getSchool(schoolId);
        if(entity==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        SchoolDto dto = new SchoolDto();
        dto.id = entity.id;
        dto.name = entity.name;
        dto.totalStudent = entity.totalStudent;
        dto.totalTeacher = entity.totalTeacher;

        return dto;
    }

    @GetMapping()
    public SchoolListDto getSchoolList(@RequestParam("page") int page , @RequestParam("size") int size){
        ArrayList<SchoolEntity> schoolList = this.schoolService.findSchool();
        ArrayList<SchoolDto> list = new ArrayList<>();
        for(SchoolEntity entity : schoolList) {
            SchoolDto dto = new SchoolDto();
            dto.id = entity.id;
            dto.name = entity.name;
            dto.totalTeacher = entity.totalTeacher;
            dto.totalStudent = entity.totalStudent;
            list.add(dto);
        }
        SchoolListDto dto = new SchoolListDto();
        dto.schools = list;
        return dto;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addSchool(@RequestBody() SchoolDto newSchool){
        SchoolEntity entity = new SchoolEntity();
        entity.name = newSchool.name;
        entity.id = newSchool.id;
        entity.totalStudent = newSchool.totalStudent;
        entity.totalTeacher = newSchool.totalTeacher;
        this.schoolService.addSchool(entity);
    }

}
