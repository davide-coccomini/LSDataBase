/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workinggroup.task1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class PublisherManager {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    
    public void setup(){
        factory = Persistence.createEntityManagerFactory("bookshop");
    }
    
    public void exit(){
        
    }
    public void create(String name, String location, List<Book> books){
        Publisher p = new Publisher();
        p.setName(name);
        p.setLocation(location);
        p.setBooks(books);
         
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(p);
            entityManager.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            entityManager.close();
        }
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
}