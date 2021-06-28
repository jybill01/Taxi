package umtaxi.springserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import umtaxi.springserver.dto.*;
import umtaxi.springserver.model.SchoolEntity;
import umtaxi.springserver.model.TaxiEntity;
import umtaxi.springserver.model.UserEntity;
import umtaxi.springserver.repository.TaxiRepository;
import umtaxi.springserver.service.TaxiService;
import umtaxi.springserver.service.UserService;

import java.util.ArrayList;
import java.util.regex.MatchResult;

@RestController()
@RequestMapping(path = "taxis")
public class TaxiController {

    private TaxiService taxiService;
    private UserService userService;

    public TaxiController(TaxiService taxiService, UserService userService){
        this.taxiService = taxiService;
        this.userService = userService;
    }

    @GetMapping()
    public TaxiListDto getTaxiList(@RequestParam("page") int page , @RequestParam("size") int size){
        ArrayList<TaxiEntity> taxiList = this.taxiService.findTaxi();
        ArrayList<TaxiDto> list = new ArrayList<>();
        for(TaxiEntity entity : taxiList) {
            TaxiDto dto = new TaxiDto();
            dto.driver = entity.driver;
            dto.latitude = entity.latitude;
            dto.longitude = entity.longitude;
            list.add(dto);
        }
        TaxiListDto dto = new TaxiListDto();
        dto.taxis = list;
        return dto;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addTaxi(@RequestBody()TaxiDto dto){
        TaxiEntity taxi = new TaxiEntity();
        taxi.driver = dto.driver;
        taxi.taxiNumber = dto.taxiNumber;
        taxi.latitude = dto.latitude;
        taxi.longitude = dto.longitude;
        this.taxiService.addTaxiPosition(taxi);
    }

    @PostMapping("/match")
    public MatchResultDto matchTaxi(@RequestBody() MatchDto dto){ //dto.start 시작
        UserEntity user = this.userService.findUserById(dto.userId);
        if(user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        TaxiEntity match = this.taxiService.match(dto.startLatitude,dto.startLatitude,dto.endLatitude,dto.endLongitude,dto.limitTime,dto.userId);
        if(match == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        int estimateTime = this.taxiService.calculateEstimateTime(match.latitude,match.longitude,dto.startLatitude,dto.endLatitude);

        MatchResultDto result = new MatchResultDto();
        result.driver = match.driver;
        result.taxiNumber = match.taxiNumber;
        result.arrivalTime = estimateTime;
        return result;
    }
}
