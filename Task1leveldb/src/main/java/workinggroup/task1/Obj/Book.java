package workinggroup.task1.Obj;
 
import java.util.List;
import org.json.JSONObject;

public class Book{
    
    private int idBOOK;

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

    public Book(JSONObject jbook) {
        this.idBOOK = (int) jbook.get("idBOOK");
        this.title = (String) jbook.get("title");
        this.price = Double.parseDouble((String) jbook.get("price"));
        this.numPages = Integer.parseInt((String) jbook.get("numPages"));
        this.publicationYear = Integer.parseInt((String) jbook.get("publicationYear"));
        this.category = (String) jbook.get("category");
        this.quantity = Integer.parseInt((String) jbook.get("quantity"));  
    }

    public int getIdBOOK() {
        return idBOOK;
    }
   
    public List<Author> getAuthors() {
        return authors;
    }
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
        
    public void setIdBOOK(int idBOOK) {
        this.idBOOK = idBOOK;
    }
     
    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getNumPages() {
        return numPages;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }
   
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
