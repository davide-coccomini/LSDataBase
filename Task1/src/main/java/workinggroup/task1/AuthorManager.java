package workinggroup.task1;

import java.util.List;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;

public class AuthorManager{

    private final JpaManager jmanager;
    
    public AuthorManager (JpaManager jm){
        jmanager = jm;
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
        Author a = null;
        a = jmanager.findById(Author.class, authorId);
        
        return a;
    }
    /* Finds a list of authors with given surname and returns it */
    public List<Author> findBySurname(final String surname) {   
        return jmanager.findListByStringField(Author.class, "lastName", surname);
    }
    /* Deletes the author with given id */
    public void delete(int authorId){
        jmanager.deleteById(Author.class, authorId);
    }
    /* Selects all authors from the DB and returns the result as an observable list. 
    Called by "submit_Button" in UICOntroller.java */
    public ObservableList<Object> selectAllAuthors(){
        return jmanager.selectAll(Author.class);
    }
}
