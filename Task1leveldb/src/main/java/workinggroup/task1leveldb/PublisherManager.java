/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workinggroup.task1leveldb;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class PublisherManager extends DatabaseManager{
    
    private int nextId;
    
    public int getNextId(){
        return nextId;   
    }
 
    
    public void create(String name, String location, List<Book> books){
        JSONObject item = new JSONObject();
        
        item.put("name", name);
        item.put("location", location);

        
        super.createCommit(getNextId(),item);
    }
    public Publisher read(int publisherId){
        
        Publisher p = null;
        try{
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
             
        }
        return p;
    }
    public void delete(int publisherId){
        try{
           
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
           
        }
    }
        public ObservableList<Object> selectAllPublishers(){
        System.out.println("SelectAllPublishers()");
         ObservableList<Object> publishers = null;
        try{
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            
        }
        
        
        return publishers;
    }
}