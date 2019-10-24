/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workinggroup.task1;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.TypedQuery;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class PublisherManager extends JpaManager{
     
    public void create(String name, String location, List<Book> books){
        Publisher p = new Publisher();
        p.setName(name);
        p.setLocation(location);
        p.setBooks(books);
         
        super.createCommit(p);
    }
    public Publisher read(int publisherId){
        
        Publisher p = null;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            p = entityManager.find(Publisher.class, publisherId);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
        return p;
    }
    public void delete(int publisherId){
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Publisher reference = entityManager.getReference(Publisher.class,publisherId);
            entityManager.remove(reference);
            entityManager.getTransaction().commit();
            //TODO: Update table
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
    }
        public ObservableList<Object> selectAllPublishers(){
        System.out.println("SelectAllPublishers()");
        entityManager = factory.createEntityManager();
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