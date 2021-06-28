package umtaxi.springserver.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity(name = "school")
public class SchoolEntity {
    @Id
    @GeneratedValue
    public long id;
    public String name;
    public int totalStudent;
    public int totalTeacher;
}
