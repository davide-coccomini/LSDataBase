package workinggroup.task1;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class PublisherManager{
     
    private final EntityManager entityManager;
    private final JpaManager jmanager;
    
    public PublisherManager(JpaManager jm){
        jmanager = jm;
        entityManager = jm.getEntityManager();
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
        Publisher p = null;
        try{
            entityManager.getTransaction().begin();
            p = entityManager.find(Publisher.class, publisherId);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        
        return p;
    }
    /* Finds a publisher with given name,
    we suppose that every publisher has a distinctive name, thus it is impossible to have more publishers woth te same name */
    public Publisher findByName(final String name) {
        //Bulds a criteria for the query "SELECT p FROM Publisher p WHERE p.name = name"
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Publisher> criteria = builder.createQuery(Publisher.class);
        Root<Publisher> from = criteria.from(Publisher.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("name"), name));
        TypedQuery<Publisher> tq = entityManager.createQuery(criteria);
        try {
            return tq.getSingleResult();
        } catch (final NoResultException ex) {
            return null;
        }  
    }
    /* Deletes the publisher with given id */
    public void delete(int publisherId){
        try{
            entityManager.getTransaction().begin();
            Publisher reference = entityManager.getReference(Publisher.class,publisherId);
            entityManager.remove(reference);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        
    }
    /* Selects all publishers from the DB and returns the result as an observable list. 
    Called by "submit_Button" in UICOntroller.java */
    public ObservableList<Object> selectAllPublishers(){
        entityManager.getTransaction().begin();
        System.out.println("Printing all publishers...");
        String query = "SELECT p FROM Publisher p";
        TypedQuery<Object> tq = entityManager.createQuery(query,Object.class);
        ObservableList<Object> publishers = null;
        try{
            publishers = FXCollections.observableList(tq.getResultList());
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }

        return publishers;
    }
}