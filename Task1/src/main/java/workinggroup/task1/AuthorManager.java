

package workinggroup.task1;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;


public class AuthorManager{
  
    private EntityManager entityManager;
    private JpaManager jmanager;
    
    public AuthorManager (JpaManager jm){
        jmanager = jm;
        entityManager = jm.getEntityManager();
    }
    public void create(String firstName, String lastName, String biography, List<Book> books){
        Author a = new Author();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setBiography(biography);
        a.setBooks(books);
         
        jmanager.createCommit(a);
    }
    public Author read(int authorId){
        Author a = new Author();
        try{
            entityManager.getTransaction().begin();
            a = entityManager.find(Author.class, authorId);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return a;
    }
    public void delete(int authorId){
        try{
            entityManager.getTransaction().begin();
            Author reference = entityManager.getReference(Author.class,authorId);
            entityManager.remove(reference);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public ObservableList<Object> selectAllAuthors(){
        System.out.println("Selectallauthors()");
        String query = "SELECT a FROM Author a";
        TypedQuery<Object> tq = entityManager.createQuery(query,Object.class);
        ObservableList<Object> authors = null;
        try{
            authors = FXCollections.observableList(tq.getResultList());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
        return authors;
    } 
}
