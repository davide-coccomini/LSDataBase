
package workinggroup.task1;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;

public class AuthorManager {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    
    public void setup(){
        factory = Persistence.createEntityManagerFactory("bookshop");
    }
    
    public void exit(){
        
    } 
    
    
     public void create(String firstName, String lastName, String biography, List<Book> books){
        Author a = new Author();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setBiography(biography);
        a.setBooks(books);
         
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(a);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
    }
    public Author read(int authorId){
        Author a = new Author();
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            a = entityManager.find(Author.class, authorId);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        return a;
    }
    public void delete(int authorId){
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Author reference = entityManager.getReference(Author.class,authorId);
            entityManager.remove(reference);
            entityManager.getTransaction().commit();
            //TODO: Update table
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
    }
        public ObservableList<Object> selectAllAuthors(){
        System.out.println("Selectallauthors()");
        entityManager = factory.createEntityManager();
        String query = "SELECT a FROM Author a";
        TypedQuery<Object> tq = entityManager.createQuery(query,Object.class);
        ObservableList<Object> authors = null;
        try{
            authors = FXCollections.observableList(tq.getResultList());
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        
        
        return authors;
    }
}
