package workinggroup.task1;
 
 
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class JpaManager {
      
    EntityManagerFactory factory;
    EntityManager entityManager;
    
    public JpaManager(){
        factory = Persistence.createEntityManagerFactory("bookshop");
        
    }
 
    public void exit(){
        factory.close();
    } 
    
    /* Insert an Object a into the database */
    public void createCommit(Object a){
        entityManager = factory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(a);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
    }
    
    /* Given a set of (Table,Id), fetch a row from the database */
    public <T extends Object> T findById(Class<T> type, int id){
        T res=null;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            res = entityManager.find(type, id);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        return res;
    }
      
    /* Given a set of (Table,Id), delete a row from the database */
    public <T extends Object> void deleteById(Class<T> type, int id){
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            T reference = entityManager.getReference(type,id);
              entityManager.remove(reference);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
         
    }
    
    /* extract a SINGLE element from a table, given a value (string) in the selected column */
    public <T extends Object> T findByStringField(Class<T> type, String field, String value){
         
        entityManager = factory.createEntityManager();
        //Bulds a criteria for the query "SELECT p FROM 'type' p WHERE p.'field' = 'value' "
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> from = criteria.from(type);
        criteria.select(from);
        criteria.where(builder.equal(from.get(field), value));
        TypedQuery<T> tq = entityManager.createQuery(criteria);
        T res = null;
        try {
            res = tq.getSingleResult();
        } catch (final NoResultException ex) {
            return null;
        }finally{
            entityManager.close();
        }  
        return res;
    }
    
    /* extract a LIST of elements from a table, given a value (string) in the selected column */
    public <T extends Object> List<T> findListByStringField(Class<T> type, String field, String value){
         
        entityManager = factory.createEntityManager();
        //Bulds a criteria for the query "SELECT * FROM 'type' p WHERE p.'field' = 'value' "
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
     
        javax.persistence.criteria.CriteriaQuery<T> criteria = builder.createQuery(type);
      
        Root<T> from = criteria.from(type);
        criteria.select(from);
        criteria.where(builder.equal(from.get(field), value));
        TypedQuery<T> tq = entityManager.createQuery(criteria);
        List<T> res = null;
        try {
            res = tq.getResultList();
        } catch (final NoResultException ex) {
            return null;
        }finally{
            entityManager.close();
        }  
        return res;
    }  
      
    /* fetch all the elements from a given table */
    public <T extends Object> ObservableList<Object> selectAll(Class<T> type){
    
        String table = "Publisher";
        if(type==Publisher.class){
            table = "Publisher";
        }else
        if(type==Book.class){
            table = "Book";
        }else
        if(type==Author.class){
            table = "Author";
        }
        
        System.out.println("Printing all "+table+"...");
        String query = "SELECT p FROM "+table+" p";
        ObservableList<Object> res = null;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<Object> tq = entityManager.createQuery(query,Object.class);
            res = FXCollections.observableList(tq.getResultList());
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }

        return res;
    }
    /* Overwrite the Object o in the database with the local copy passed as parameter */
    public <T extends Object> void update(Class<T> type, Object o){
    
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(o);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        
    }
}
