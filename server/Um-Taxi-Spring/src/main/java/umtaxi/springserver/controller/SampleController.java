package umtaxi.springserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umtaxi.springserver.dto.SampleDto;
import umtaxi.springserver.service.TaxiService;

@RestController
public class SampleController {
    private TaxiService taxiService;

    @Autowired
    public SampleController(TaxiService taxiService){
        this.taxiService = taxiService;
    }

    @GetMapping("/")
    public double sampleRequest(){
        return this.taxiService.calculateDistance(37.508809,127.125975, 37.509596, 127.124103);
    }
}
