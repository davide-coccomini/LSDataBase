
package workinggroup.task1.Obj;

import java.util.List;
import javax.persistence.CascadeType;
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
       
    private int idAUTHOR;
    private String firstName;
    private String lastName;
    private String biography;
    List<Book> books;
            
    public Author(String firstName, String lastName, String biography, List<Book> books){
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.books = books;
    }

    public Author() {
        
    }

     
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idAUTHOR")
    public int getIdAUTHOR() {
        return idAUTHOR;
    }

    public void setIdAUTHOR(int id) {
        this.idAUTHOR = id;
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
    @ManyToMany(mappedBy = "authors", cascade = CascadeType.REMOVE)
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
