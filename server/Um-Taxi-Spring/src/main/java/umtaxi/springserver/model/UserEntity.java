package umtaxi.springserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "user")
public class UserEntity {

    @Id()
    @GeneratedValue()
    public long id;
    public String name;
    public String phoneNumber;
    public String password;


}
