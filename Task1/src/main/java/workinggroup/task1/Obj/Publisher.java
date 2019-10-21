package workinggroup.task1.Obj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Publisher{
    private int id;
    private String name;
    private String location;
      
    public Publisher(String name, String location){
        this.name = name;
        this.location = location; 
    }

    @Id    
    @Column(name = "idPUBLISHER")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

}
