package umtaxi.springserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "taxi")
public class TaxiEntity {
    @Id()
    @GeneratedValue()
    public long id;
    public String taxiNumber;
    public String driver;
    public double latitude;
    public double longitude;

}
