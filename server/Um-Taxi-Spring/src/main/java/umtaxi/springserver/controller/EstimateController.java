package umtaxi.springserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umtaxi.springserver.dto.EstimateDto;
import umtaxi.springserver.service.TaxiService;

@RestController
@RequestMapping(path="/estimate")
public class EstimateController {
    TaxiService taxiService;

    @Autowired
    public EstimateController(TaxiService taxiService) { this.taxiService =taxiService;}

    @GetMapping()
    public EstimateDto getEstimate(@RequestParam("startLatitude") double startLatitude,
                                   @RequestParam("startLongitude") double startLongitude,
                                   @RequestParam("endLatitude") double endLatitude,
                                   @RequestParam("endLongitude") double endLongitude){
        EstimateDto estimateDto = new EstimateDto();

        estimateDto.estimateDistance = taxiService.calculateDistance(startLatitude,startLongitude,endLatitude,endLongitude);
        estimateDto.estimateTime = taxiService.calculateEstimateTime(startLatitude, startLongitude, endLatitude, endLongitude);
        estimateDto.estimateCost = taxiService.calculateEstimateCost(estimateDto.estimateDistance);
        return  estimateDto;
    }

}
