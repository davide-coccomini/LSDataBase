package workinggroup.task1.Obj;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="publisher")
public class Publisher{
    private int idPUBLISHER;

    private String name;
    private String location;
    private List<Book> books;

    public Publisher() {
    }
    
    public Publisher(String name, String location){
        this.name = name;
        this.location = location; 
    }

    @Id   
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idPUBLISHER")
    public int getIdPUBLISHER() {
        return idPUBLISHER;
    }

    public void setIdPUBLISHER(int id) {
        this.idPUBLISHER = id;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }
    @Column(name = "location")
    public String getLocation() {
        return location;
    }
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
