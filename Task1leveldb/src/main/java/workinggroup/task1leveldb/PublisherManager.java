/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workinggroup.task1leveldb;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.iq80.leveldb.DBIterator;
import static org.iq80.leveldb.impl.Iq80DBFactory.asString;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import org.json.JSONObject;
import workinggroup.task1.Obj.Publisher;

public class PublisherManager{
    
    DatabaseManager dbmanager;

    public PublisherManager(DatabaseManager parentManager) {
        this.dbmanager = parentManager;
    }

    public String getNextPublisherId(){
        return "publisher-"+dbmanager.getNextId();    
    }
 
    
    public void create(String name, String location){
        dbmanager.open();
            JSONObject item = new JSONObject();
            item.put("idPUBLISHER", dbmanager.getNextId());
            item.put("name", name);
            item.put("location", location);

            dbmanager.createCommit(getNextPublisherId(),item);
        
        dbmanager.close();
    }
    public Publisher read(int publisherId){  
        System.out.println("looking for publisher with id "+publisherId);
        Publisher p = null;
         
        try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
            keyIterator.seekToFirst();

            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
                String[] splittedString = key.split("-");

                if(!"publisher".equals(splittedString[0])){
                    keyIterator.next();
                    continue;
                    }

                String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
                JSONObject jpublisher = new JSONObject(resultAttribute);

                if(jpublisher.getInt("idPUBLISHER")==publisherId){
                    p=new Publisher(jpublisher);
                    break;
                }
                keyIterator.next();
                    
                }
            }
        catch(Exception ex){
            ex.printStackTrace();
        } 
         
        return p;
    }
    public void delete(int publisherId){  
        System.out.println("Delete publisher");
        dbmanager.open();
        try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
            keyIterator.seekToFirst();

            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
                String[] splittedString = key.split("-");

                String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
                if("author".equals(splittedString[0]) || resultAttribute == null){ // There is no relationship between publisher and author
                    keyIterator.next();
                    continue;
                }
                
            
               System.out.println("ciao");
                System.out.println(dbmanager.getDB().get(bytes(key)));
                
                if("publisher".equals(splittedString[0])){ // Delete the publisher
                    JSONObject jpublisher = new JSONObject(resultAttribute);
                    if(jpublisher.getInt("idPUBLISHER")==publisherId){
                        dbmanager.getDB().delete(bytes(key));
                        continue;
                    }
                }else{ // Delete the book referring to this publisher (Implementing cascade)
                    JSONObject jbook = new JSONObject(resultAttribute);
                    if(jbook.getInt("publisher")==publisherId){
                        dbmanager.getDB().delete(bytes(key));
                        continue;
                    }
                }
                keyIterator.next();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        } 
        dbmanager.close();
    }
    public ObservableList<Object> selectAllPublishers(){
        System.out.println("selectAllPublishers()");
        dbmanager.open();
            ObservableList<Object> result = FXCollections.observableArrayList();

            try{
                try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
                    keyIterator.seekToFirst();

                    while (keyIterator.hasNext()) {
                        String key = asString(keyIterator.peekNext().getKey());
                        String[] splittedString = key.split("-");

                        dbmanager.incrementNextId(Integer.parseInt(splittedString[1]));

                        if(!"publisher".equals(splittedString[0])){
                            keyIterator.next();
                            continue;
                        }

                        String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
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
        dbmanager.close();
        return result;
    } 
}