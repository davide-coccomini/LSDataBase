
package workinggroup.task1.Obj;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="author")
public class Author{
       
    private int id;
    private String firstName;
    private String lastName;
    private String biography;
    List<Book> books;
            
    public Author(String firstName, String lastName, String biography){
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }

    public Author() {
        
    }

     
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idAUTHOR")
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
    @ManyToMany(mappedBy="authors")
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    

    
}
