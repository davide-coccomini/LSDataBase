package workinggroup.task1;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaManager {
      
    EntityManagerFactory factory;
    EntityManager entityManager;
    
    public JpaManager(){
        factory = Persistence.createEntityManagerFactory("bookshop");
        
    }
    public EntityManager startEntityManager(){
        return factory.createEntityManager();
    }
    public void exit(){
        factory.close();
    } 
    
    /* Called by the functions "create" in book, author and publisher */
    public void createCommit(Object a){
        entityManager = startEntityManager();
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
    
    public EntityManager getEntityManager(){
        return entityManager;
    }
   
}
