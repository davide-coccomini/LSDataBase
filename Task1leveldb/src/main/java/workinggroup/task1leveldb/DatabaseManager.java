package workinggroup.task1leveldb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import org.iq80.leveldb.DB;
import static org.iq80.leveldb.impl.Iq80DBFactory.factory;
import org.iq80.leveldb.Options;
import org.json.JSONObject;

public class DatabaseManager {

    private DB db;
    private int nextId;
    private AuthorManager amanager;
    private BookManager bmanager;
    private PublisherManager pmanager;
    
    public DatabaseManager()  {
        nextId = 0;
    }
    
    public AuthorManager getAmanager() {
        return amanager;
    }

    public BookManager getBmanager() {
        return bmanager;
    }

    public PublisherManager getPmanager() {
        return pmanager;
    }
    public void open(){
      try {
          Options options = new Options();

          db = factory.open(new File("db"), options);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getNextId() {
          
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }
    
    public void incrementNextId(int newId){
        System.out.println("set: "+newId);
        int oldId=nextId;
        
        this.nextId=(newId>=oldId)?(newId+1):(oldId);
    }
    
    public void deleteValuesFromUser(ArrayList<String> toDelete){ 
// TODO SPOSTARE O CAMBIARE
        
        System.out.println("dentro Delete: " + toDelete.size());
        for (String key : toDelete) {
            
        }
       
    }
 
    public void close(){
       try{
           db.close();
       }catch(IOException e){
           e.printStackTrace();
       }
    }

    public DB getDB(){
        return db;
    }
    public void createCommit(String key,JSONObject item){
        
        db.put(bytes(key), bytes(item.toString()));  
        
    }

    void init(AuthorManager amanager, BookManager bmanager, PublisherManager pmanager) {
        
        this.amanager=amanager;
        this.bmanager=bmanager;
        this.pmanager=pmanager;
        
    }
}
