
package workinggroup.task1.Obj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Author{
       
    private int id;
    private String firstName;
    private String lastName;
    private String biography;

    public Author() {
    }
            
    public Author(String firstName, String lastName, String biography){
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }
         
     
    @ManyToMany(mappedBy="book")
    @Column(name = "idAUTHOR")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    
    @Column(name = "biography")
    public String getBiography() {
        return biography;
    }
}
