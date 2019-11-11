package workinggroup.task1;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaManager {
      
    EntityManagerFactory factory;
    EntityManager entityManager;
    
    public JpaManager(){
        factory = Persistence.createEntityManagerFactory("bookshop");
        entityManager = factory.createEntityManager();
    }
    
    public void exit(){
        // unreachable code
        entityManager.close();
        factory.close();
    } 
    
    public void createCommit(Object a){
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(a);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public EntityManager getEntityManager(){
        return entityManager;
    }
   
}
