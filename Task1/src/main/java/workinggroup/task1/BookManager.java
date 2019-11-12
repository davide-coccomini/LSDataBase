package workinggroup.task1;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class BookManager {
    private final JpaManager jmanager;
    private final AuthorManager amanager;
    private final PublisherManager pmanager;
    private final EntityManager entityManager;
    
    public BookManager(JpaManager j, AuthorManager a, PublisherManager p){
        amanager = a;
        pmanager = p;
        jmanager = j;
        entityManager = j.getEntityManager();
    }
    /* Creates a new Book and puts it into the DB */
    public void create(String title, String numPages, String quantity, String category, String price, String publicationYear, String[] authorsId, String publisherId) {
        System.out.println("creating book: " + title + " written by " + authorsId[0] + " published by " + publisherId);
        /*List<Book> list = findByTitle(title);   //checking if the book is in the DB already
        if(list != null){
            for (Book item : list) {
                //checking if the first author, the category, the publisher and the edition are the same
                if(item.getAuthors().get(0).equals(authorsId) && item.getCategory().equals(category) && 
                    Integer.toString(item.getPublisher().getIdPUBLISHER()).equals(publisherId)       &&
                    Integer.toString(item.getPublicationYear()).equals(publicationYear)){
                    
                    if(!Integer.toString(item.getQuantity()).equals(quantity))
                        update(item.getIdBOOK(), Integer.parseInt(quantity));
                    
                    System.out.println("The book is in the database already");
                    return;
                }  
            }
        }*/
        Book b = new Book();
        b.setTitle(title);
        b.setNumPages(Integer.parseInt(numPages));
        b.setQuantity(Integer.parseInt(quantity));
        b.setCategory(category);
        b.setPrice(Double.parseDouble(price));
        b.setPublicationYear(Integer.parseInt(publicationYear));
        
        List<Author> authors = new ArrayList<>();
   
        for(int i=0; i < authorsId.length; i++){
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
    
    /* Finds a list of books with given title */
    public List<Book> findByTitle(final String title) {
        //Bulds a criteria for the query "SELECT b FROM Book b WHERE b.title = title"
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        Root<Book> from = criteria.from(Book.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("title"), title));
        TypedQuery<Book> tq = entityManager.createQuery(criteria);
        try {
            return tq.getResultList();
        } catch (final NoResultException ex) {
            return null;
        }  
    }
    /* Updates the quantity in stock for the book with given id */
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
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
