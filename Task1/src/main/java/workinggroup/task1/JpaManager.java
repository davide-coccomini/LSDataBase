/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workinggroup.task1;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* non funziona
@NamedQuery(name = "Author.selectAllAuthors", query = "SELECT a FROM Author a")
*/

public class JpaManager {
      
    EntityManagerFactory factory;
    EntityManager entityManager;
    
    public JpaManager(){
        factory = Persistence.createEntityManagerFactory("bookshop");
    }
    
    public void exit(){
        
    } 
    
    public void createCommit(Object a){
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
   
}
