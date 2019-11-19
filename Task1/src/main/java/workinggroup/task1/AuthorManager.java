package workinggroup.task1;

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

public class AuthorManager{
  
    private final EntityManager entityManager;
    private final JpaManager jmanager;
    
    public AuthorManager (JpaManager jm){
        jmanager = jm;
        entityManager = jm.getEntityManager();
    }
    /* Creates a new Author and puts it into the DB */
    public void create(String firstName, String lastName, String biography, List<Book> books){
        System.out.println("creating author: " + firstName + " " + lastName);
        //checking if the book is in the DB already
        List<Author> list = findBySurname(lastName);    
        if(list != null){   //checks if the DB already contains an author with the same name and surname
            for (Author item : list) {
                if (item.getFirstName().equals(firstName)) {
                    System.out.println("This author is already in the database!");
                    return;
                }
            }
        }
        Author a = new Author();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setBiography(biography);
        a.setBooks(books);
         
        jmanager.createCommit(a);                       //see JpaManager.java
    }
    /* Finds an author by id */
    public Author read(int authorId){
        Author a = new Author();
        try{
            entityManager.getTransaction().begin();
            a = entityManager.find(Author.class, authorId);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return a;
    }
    /* Finds a list of authors with given surname and returns it */
    public List<Author> findBySurname(final String surname) {
        //Bulds a criteria for the query "SELECT a FROM Author a WHERE a.firstName = name"
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        Root<Author> from = criteria.from(Author.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("lastName"), surname));
        
        TypedQuery<Author> tq = entityManager.createQuery(criteria);
        try {
            return tq.getResultList();
        } catch (final NoResultException ex) {
            return null;
        }  
    }
    /* Deletes the author with given id */
    public void delete(int authorId){
        try{
            entityManager.getTransaction().begin();
            Author reference = entityManager.getReference(Author.class, authorId);
            entityManager.remove(reference);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    /* Selects all authors from the DB and returns the result as an observable list. 
    Called by "submit_Button" in UICOntroller.java */
    public ObservableList<Object> selectAllAuthors(){
        System.out.println("Printing all authors...");
        String query = "SELECT a FROM Author a";
        TypedQuery<Object> tq = entityManager.createQuery(query, Object.class);
        ObservableList<Object> authors = null;
        try{
            authors = FXCollections.observableList(tq.getResultList());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return authors;
    } 
}
