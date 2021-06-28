package umtaxi.springserver.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name="matched")
public class MatchEntity {
    @Id
    @GeneratedValue

    @Temporal(TemporalType.TIMESTAMP)
    public Date matchedAt;

    public long id;
    public long taxiId;
    public long userId;

    public double startLatitude; //내 위치
    public double startLongitude;
    public double endLatitude; //가고 싶은데 위치
    public double endLongitude;

    public double estimateTime;
    public double estimateDistance;
    public int estimateCost;
    public int matchingTime;

}
