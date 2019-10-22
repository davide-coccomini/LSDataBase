
package workinggroup.task1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

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
        
        Author a = null;
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
}
