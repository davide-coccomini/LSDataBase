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


    /* la lista degli autori sta nel bean book perché quando registri un autore
    non sai la lista dei libri che scriverà, mentre quando aggiungi un libro 
    puoi registrare quali autori lo hanno scritto */
    private List<Author> authors;
   
    
    public Book(int ID,String title, double price, int numPages, int year, String category, int quantity, List<Author> authors, Publisher publisher){
        this.idBOOK=ID;
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
        System.out.println("Book()");
    }

    public Book(JSONObject jbook) {
        this.idBOOK = (int) jbook.get("idBOOK");
        this.title = (String) jbook.get("title");
        this.price = Double.parseDouble((String) jbook.get("price"));
        this.numPages = Integer.parseInt((String) jbook.get("numPages"));
        this.publicationYear = Integer.parseInt((String) jbook.get("publicationYear"));
        this.category = (String) jbook.get("category");
        this.quantity = Integer.parseInt((String) jbook.get("quantity"));
        
        
        String authorsId = (String)jbook.get("authors");
        //TODO: Iterare sugli id ricevuti dall'utente, prelevare le info dell'autore tramite un'apposita funzione che deve essere fatta in authormanager, creare un oggetto Autore e metterlo in una lista che sarà poi la List<Authors>
        
        String publisherId = (String)jbook.get("publisher");
        //TODO: Leggere le informazioni del publisher partendo dal suo id passandolo ad una funzione che deve essere fatta in publisher manager, creare un oggetto Publisher e metterlo nella variabile this.publisher
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
