package workinggroup.task1.Obj;
 
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "book")
@SequenceGenerator(name = "Placeholder_name", sequenceName = "placeholder_Table")
public class Book implements Serializable{
    
    private int idBOOK;         //automatically generated
    private String title;
    private double price;
    private int numPages;
    private int publicationYear;
    private String category;
    private int quantity;
    private Publisher publisher;

    /* La lista degli autori e' nel bean book perché quando un nuovo autore viene registrato
    non e' nota la lista dei libri che scriverà, mentre quando viene aggiunto un nuovo libro 
    se ne conoscono gia' gli autori */
    private List<Author> authors;
       
    public Book(int ID,String title, double price, int numPages, int year, String category, int quantity, List<Author> authors, Publisher publisher){
        this.idBOOK = ID;
        this.title = title;
        this.price = price;
        this.numPages = numPages;
        this.publicationYear = year;
        this.category = category;
        this.quantity = quantity;
        this.authors = authors;
        this.publisher = publisher;
    }

    public Book() {
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBOOK",  nullable = false, length = 10)
    public int getIdBOOK() {
        return idBOOK;
    }
 
    /* A book can have more authors */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_has_author",
		joinColumns = {@JoinColumn(name = "idBOOK")},
		inverseJoinColumns = {@JoinColumn(name = "idAUTHOR")})
    
    public List<Author> getAuthors() {
		return authors;
	}
    public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
    
    public void setIdBOOK(int idBOOK) {
        this.idBOOK = idBOOK;
    }
  
    @Column(name = "title",  nullable = false, length = 100)
    public String getTitle() {
        return title;
    }
    
    @Column(name = "price", nullable = false)
    public double getPrice() {
        return price;
    }
    
    @Column(name = "numPages", nullable = false)
    public int getNumPages() {
        return numPages;
    }
    
    @Column(name = "publicationYear", nullable = false, length = 4)
    public int getPublicationYear() {
        return publicationYear;
    }
    
    @Column(name = "category", nullable = false, length = 45)
    public String getCategory() {
        return category;
    }
    
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }
    
    /* A publisher can publish more books */
    @ManyToOne
    @JoinColumn(name = "PUBLISHER_idPUBLISHER")
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
