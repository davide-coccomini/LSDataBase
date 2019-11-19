package workinggroup.task1.Obj;

import java.io.Serializable;
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
@Table(name = "author")
public class Author implements Serializable{
       
    private int idAUTHOR;       //automatically generated
    private String firstName;
    private String lastName;
    private String biography;
    List<Book> books;           //Max 511 char
            
    public Author(String firstName, String lastName, String biography, List<Book> books){
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.books = books;
    }

    public Author() {
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    /* A book can have more authors, every time an author is removed form the DB all his/hers books are removed as well */
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
