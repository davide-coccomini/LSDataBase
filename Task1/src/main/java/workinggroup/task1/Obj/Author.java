
package workinggroup.task1.Obj;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
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

     
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    public int getIdAuthor() {
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
    

    
}
