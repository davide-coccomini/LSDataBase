/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workinggroup.task1leveldb;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.iq80.leveldb.DBIterator;
import static org.iq80.leveldb.impl.Iq80DBFactory.asString;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import org.json.JSONObject;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class PublisherManager extends DatabaseManager{
    
    private int nextId;
    
    public String getNextId(){// TODO: Generare adeguatamente questa chiave (magari iterare su tutti i publisher alla ricerca dell'id più alto e incrementarlo di 1)
        return "publisher-0";    
    }
 
    
    public void create(String name, String location){
        JSONObject item = new JSONObject();
        item.put("idPUBLISHER", 0);
        item.put("name", name);
        item.put("location", location);

        
        super.createCommit(getNextId(),item);
        
        this.close();
    }
    public Publisher read(int publisherId){ // TODO: Aggiornare questa funzione
        
        Publisher p = null;
        try{
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
             
        }
        return p;
    }
    public void delete(int publisherId){ // TODO: Aggiornare questa funzione
        try{
           
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
           
        }
    }
    public ObservableList<Object> selectAllPublishers(){
        System.out.println("selectAllPublishers()");
 
        ObservableList<Object> result = FXCollections.observableArrayList();
  
        try{
            try (DBIterator keyIterator = super.getDB().iterator()) {
                keyIterator.seekToFirst();
                
                while (keyIterator.hasNext()) {
                    String key = asString(keyIterator.peekNext().getKey());
                    String[] splittedString = key.split("-");
                    if(!"publisher".equals(splittedString[0])){
                        keyIterator.next();
                        continue;
                    }
                    
                    String resultAttribute = asString(super.getDB().get(bytes(key)));
                    JSONObject jpublisher = new JSONObject(resultAttribute);
                    
                    Publisher publisher = new Publisher(jpublisher);
                    result.add(publisher);
                    keyIterator.next();
                }
            }
        }
        
        catch(IOException e){
           e.printStackTrace();
        }
        this.close();
        return result;
    } 
}