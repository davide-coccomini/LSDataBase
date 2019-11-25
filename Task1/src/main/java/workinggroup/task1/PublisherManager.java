package workinggroup.task1;

import java.util.List;
import javafx.collections.ObservableList;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class PublisherManager{
     private final JpaManager jmanager;
    
    public PublisherManager(JpaManager jm){
        jmanager = jm;
    }
    /* Creates a new Publisher and puts it into the DB */
    public void create(String name, String location, List<Book> books){
        System.out.println("creating publisher: " + name + " " + location); 
        Publisher result = findByName(name);    //checking if the publisher is in the DB already
        if(result != null){
            System.out.println("The publisher is already in the database!");
            return;
        }
        
        Publisher p = new Publisher();
        p.setName(name);
        p.setLocation(location);
        p.setBooks(books);
         
        jmanager.createCommit(p);               //see JpaManager.java
    }
    /* Finds a publisher by id */
    public Publisher read(int publisherId){
        Publisher p = jmanager.findById(Publisher.class, publisherId);
        return p;
    }
    /* Finds a publisher with given name,
    we suppose that every publisher has a distinctive name, thus it is impossible to have more publishers woth te same name */
    public Publisher findByName(final String name) {
        return jmanager.findByStringField(Publisher.class, "name", name); 
    }
    /* Deletes the publisher with given id */
    public void delete(int publisherId){
        jmanager.deleteById(Publisher.class, publisherId);
    }
    /* Selects all publishers from the DB and returns the result as an observable list. 
    Called by "submit_Button" in UICOntroller.java */
    public ObservableList<Object> selectAllPublishers(){
       
        return jmanager.selectAll(Publisher.class);
    }
}