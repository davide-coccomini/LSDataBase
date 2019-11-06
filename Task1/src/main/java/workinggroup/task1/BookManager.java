package workinggroup.task1;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class BookManager {
    private JpaManager jmanager;
    private AuthorManager amanager;
    private PublisherManager pmanager;
    private EntityManager entityManager;
    
    public BookManager(JpaManager j, AuthorManager a, PublisherManager p){
        amanager=a;
        pmanager=p;
        jmanager=j;
        entityManager=j.getEntityManager();
    }
    public void create(String title, String numPages, String quantity, String category, String price, String publicationYear, String[] authorsId, String publisherId) {
        
        System.out.println("create()");
        Book b = new Book();
        b.setTitle(title);
        b.setNumPages(Integer.parseInt(numPages));
        b.setQuantity(Integer.parseInt(quantity));
        b.setCategory(category);
        b.setPrice(Double.parseDouble(price));
        b.setPublicationYear(Integer.parseInt(publicationYear));
        
        List<Author> authors = new ArrayList<Author>();
   
        for(int i=0; i<authorsId.length; i++){
            Author a = amanager.read(Integer.parseInt(authorsId[i]));
            authors.add(a);
        }
        
        b.setAuthors(authors);
  
        Publisher p = pmanager.read(Integer.parseInt(publisherId));
        b.setPublisher(p);
        
        jmanager.createCommit(b);
    }
    public ObservableList<Object> selectAllBooks(){
        System.out.println("Selectallbooks()");
        String query = "SELECT b FROM Book b";
        TypedQuery<Object> tq = entityManager.createQuery(query,Object.class);
        ObservableList<Object> books = null;
        try{
            books = FXCollections.observableList(tq.getResultList());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
        return books;
    } 
    public Book read(int bookId){
        
        Book b = null;
        try{
            entityManager.getTransaction().begin();
            b = entityManager.find(Book.class, bookId);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return b;
    }
    public void update(int bookId, int quantity){
        Book book = new Book();
        Book book_original = new Book();
        book_original = read(bookId);
        
        book.setIdBOOK(bookId);
        book.setQuantity(quantity);
        
        book.setTitle(book_original.getTitle());
        book.setNumPages(book_original.getNumPages());
        book.setCategory(book_original.getCategory());
        book.setPrice(book_original.getPrice());
        book.setPublicationYear(book_original.getPublicationYear());
        book.setAuthors(book_original.getAuthors());
        book.setPublisher(book_original.getPublisher());
        
        try {
  
            entityManager.getTransaction().begin();
            entityManager.merge(book);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void delete(int bookId){
        try{
            entityManager.getTransaction().begin();
            Book reference = entityManager.getReference(Book.class,bookId);
            entityManager.remove(reference);
            entityManager.getTransaction().commit();
            //TODO: Update table
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
