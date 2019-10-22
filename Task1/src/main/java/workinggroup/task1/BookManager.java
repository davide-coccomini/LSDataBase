package workinggroup.task1;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;
import static workinggroup.task1.Util.chooseType;

public class BookManager {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    
    public void setup(){
        factory = Persistence.createEntityManagerFactory("bookshop");
    }
    
    public void exit(){
        
    }
    
    public void create(String title,int numPages,int quantity,String category,double price,int publicationYear, List<Author> authors, Publisher publisher){
        Book b = new Book();
        b.setTitle(title);
        b.setNumPages(numPages);
        b.setQuantity(quantity);
        b.setCategory(category);
        b.setPrice(price);
        b.setPublicationYear(publicationYear);
        b.setAuthors(authors);
        b.setPublisher(publisher);
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(b);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
    }
    public ObservableList<Object> selectAllBooks(){
        entityManager = factory.createEntityManager();
        String query = "SELECT * FROM book";
        TypedQuery<Object> tq = entityManager.createQuery(query,Object.class);
        ObservableList<Object> books = null;
        try{
            books = FXCollections.observableList(tq.getResultList());
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        
        
        return books;
    } 
    public Book read(int bookId){
        
        Book b = null;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            b = entityManager.find(Book.class, bookId);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
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
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(book);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        // TODO: Update table
    }
    public void delete(long bookId){
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Book reference = entityManager.getReference(Book.class,bookId);
            entityManager.remove(reference);
            entityManager.getTransaction().commit();
            //TODO: Update table
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
    }
}
