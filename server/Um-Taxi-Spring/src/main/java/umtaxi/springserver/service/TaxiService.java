package umtaxi.springserver.service;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umtaxi.springserver.model.MatchEntity;
import umtaxi.springserver.model.SchoolEntity;
import umtaxi.springserver.model.TaxiEntity;
import umtaxi.springserver.repository.SchoolRepository;
import umtaxi.springserver.repository.TaxiRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TaxiService {
    private TaxiRepository taxiRepository;

    @Autowired
    public TaxiService(TaxiRepository taxiRepository) {
        this.taxiRepository = taxiRepository;
    }
    private MatchService matchService;
    private final static int KM_RADIUS = 6371;
    private final static double MAX_SPEED_PER_MIN = 1;
    private final static int DEFAULT_COST = 3800;
    private final static int EXTRA_COST = 100;
    private final static int DEFAULT_COST_DISTANCE = 2;

    public TaxiService (TaxiRepository taxiRepository, MatchService matchService){
        this.taxiRepository = taxiRepository; this.matchService = matchService;
    }

    private double degreeToRadian(double degree){
        return degree * Math.PI / 180;
    }

    public double calculateDistance (double latitude1, double longitude1, double latitude2, double longitude2){
        double distanceLatitude = degreeToRadian(latitude1 - latitude2);
        double distanceLongitude = degreeToRadian(longitude1-longitude2);
        double value = Math.pow(Math.sin(distanceLatitude/2),2) +
                Math.pow(Math.sin(distanceLongitude/2),2) * Math.cos(degreeToRadian(latitude1)) * Math.cos(degreeToRadian(latitude2));
        double c = 2 * Math.atan2(Math.sqrt(value), Math.sqrt(1-value));
        return KM_RADIUS * c;
    }

    public int calculateEstimateTime (double startLatitude, double startLongitude,double endLatitude,double endLongitude)
    {
        double distance = calculateDistance(startLatitude, startLongitude, endLatitude, endLongitude);
        distance = Math.ceil(distance);
        return (int)distance;
    }
    public int calculateEstimateCost(double distance){

        if(distance<DEFAULT_COST_DISTANCE)
            return DEFAULT_COST;
        else{
            int cost = DEFAULT_COST + 100;
            distance -= DEFAULT_COST_DISTANCE;
            int m = (int)Math.ceil(distance*1000);
            cost += 100 * ( m / 100 );
            return cost;

        }
    }

    public ArrayList<TaxiEntity> findTaxi(){
        ArrayList<TaxiEntity> taxiList = new ArrayList<>(taxiRepository.findAll());
        return taxiList;
    }

    public void addTaxiPosition(TaxiEntity taxi){
        taxiRepository.save(taxi);
    }

    public TaxiEntity match(double startLatitude, double startLongitude, double endLatitude, double endLongitude, int limit,long userId){ //내위치와 시간을 파라미터 값으로 전송한다.
        ArrayList<TaxiEntity> taxis = new ArrayList<>(this.taxiRepository.findAll());
        List<TaxiEntity> matchList = taxis.stream().filter(taxiEntity -> this.calculateEstimateTime(
                taxiEntity.latitude,taxiEntity.longitude, startLatitude,startLongitude) < limit).collect(Collectors.toList());
        double minDistance = Double.MAX_VALUE;
        TaxiEntity minDistanceTaxi = null;
        ArrayList<TaxiEntity> matched = new ArrayList(matchList);

        if(matched.size() == 0) return null;
        for(TaxiEntity taxi : matched){
            MatchEntity match = this.matchService.getMatch(taxi.id);
            if(match !=null) continue;
            double distance = this.calculateDistance(startLatitude,startLongitude,taxi.latitude,taxi.longitude);
            if(distance < minDistance){
                minDistance = distance;
                minDistanceTaxi = taxi;
            }
        }

        double estimateDistance = this.calculateDistance(startLatitude,startLongitude,endLatitude,endLongitude);
        int estimateTime = this.calculateEstimateTime(startLatitude,startLongitude,endLatitude,endLongitude);
        int estimateCost = this.calculateEstimateCost(estimateDistance);

        MatchEntity matchEntity = new MatchEntity();
        matchEntity.estimateDistance = estimateDistance;
        matchEntity.estimateCost = estimateCost;
        matchEntity.estimateTime = estimateTime;
        matchEntity.startLatitude = startLatitude;
        matchEntity.startLongitude = startLongitude;
        matchEntity.endLatitude = endLatitude;
        matchEntity.endLongitude = endLongitude;
        matchEntity.taxiId = minDistanceTaxi.id;
        matchEntity.userId = userId;
        matchEntity.matchedAt = new Date();
        this.matchService.addMatch(matchEntity);

        return minDistanceTaxi;

    }
}
