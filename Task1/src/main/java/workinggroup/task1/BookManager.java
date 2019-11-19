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
        /* checking if the book is already in the DB to avoid duplications
        if author, publisher, edition and quantity are the same nothing changes;
        if the edition is different a new book with different edition is added;
        if the quantity is different the book is updated */
        List<Book> list = findByTitle(title);
        if(list != null){
            for (Book item : list) {
                // Checking if the first author and the publisher are the same
                if( Integer.toString(item.getAuthors().get(0).getIdAUTHOR()).equals(authorsId[0]) &&
                    Integer.toString(item.getPublisher().getIdPUBLISHER()).equals(publisherId) ) {
                    // Checking if it's a new edition
                    if(Integer.toString(item.getPublicationYear()).equals(publicationYear)){    
                        // If the only difference is the quantity in stock it needs to be updated
                        if(!Integer.toString(item.getQuantity()).equals(quantity)) {
                            update(item.getIdBOOK(), Integer.parseInt(quantity));
                            System.out.println("Quantity updated");
                            return;
                        } else {
                            System.out.println("The book is already in the database!");
                            return;
                        }
                    } else {
                        System.out.println("New edition added");
                    }
                }      
            }
        }
        Book b = new Book();
        b.setTitle(title);
        b.setNumPages(Integer.parseInt(numPages));
        b.setQuantity(Integer.parseInt(quantity));
        b.setCategory(category);
        b.setPrice(Double.parseDouble(price));
        b.setPublicationYear(Integer.parseInt(publicationYear));
        
        List<Author> authors = new ArrayList<>();       //a book can have more authors
        for(int i=0; i < authorsId.length; i++){
            Author a = amanager.read(Integer.parseInt(authorsId[i]));
            authors.add(a);
        }
        b.setAuthors(authors);
  
        Publisher p = pmanager.read(Integer.parseInt(publisherId));
        b.setPublisher(p);
        
        jmanager.createCommit(b);                       //see JpaManager.java
    }
    /* Finds an author by id */
    public Book read(int bookId){
        
        Book b = null;
        try {
            entityManager.getTransaction().begin();
            b = entityManager.find(Book.class, bookId);
            entityManager.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
        }finally{
            entityManager.close();
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
        Book book_original = read(bookId);
        
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
        }finally{
            entityManager.close();
        }
        
    }
    /* Deletes the book with given id */
    public void delete(int bookId){
        try{
            entityManager.getTransaction().begin();
            Book reference = entityManager.getReference(Book.class,bookId);
            entityManager.remove(reference);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        
    }
    /* Selects all books from the DB and returns the result as an observable list. 
    Called by "submit_Button" in UICOntroller.java */
    public ObservableList<Object> selectAllBooks(){
        System.out.println("Printing all books...");
        String query = "SELECT b FROM Book b";
        entityManager.getTransaction().begin();
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

}
