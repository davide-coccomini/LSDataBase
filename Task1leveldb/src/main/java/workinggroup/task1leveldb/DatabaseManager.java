package workinggroup.task1leveldb;

import java.io.File;
import java.io.IOException;
import static java.lang.Long.*;
import java.util.ArrayList;
import java.util.List;
import org.iq80.leveldb.DBIterator;
import static org.iq80.leveldb.impl.Iq80DBFactory.asString;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import org.iq80.leveldb.DB;
import static org.iq80.leveldb.impl.Iq80DBFactory.factory;
import org.iq80.leveldb.Options;
import org.json.JSONObject;

public class DatabaseManager {

    private DB db;
    private static final String FILE_PATH="./db/Logs.log";
    
   
    public DatabaseManager()  {
      try {
          Options options = new Options();
//          FileHandler handler = new FileHandler(FILE_PATH);
          db = factory.open(new File("db"), options);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

 
    public void deleteValuesFromUser(ArrayList<String> toDelete){
        System.out.println("dentro Delete: " + toDelete.size());
        for (String key : toDelete) {
            db.delete(bytes(key));
        }
    }
    
 
    public List<String> getValuesFromUser(String object, Long id){
       
        List<String> result = new ArrayList();  
  
        try(
            DBIterator keyIterator = db.iterator();
        ){
            
            String myKey = object + "-" + id;
            keyIterator.seek(bytes(myKey));
            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
                String[] keySplit = key.split(":"); 
                if (parseLong(keySplit[1]) != id) {
                        break;
                    }
                String resultAttribute = keySplit[keySplit.length - 1] + ":" + asString(db.get(bytes(key)));
                result.add(resultAttribute);
                keyIterator.next();
            }
            keyIterator.close();
        }
        
        catch(IOException e){
           e.printStackTrace();
        }
        return result;
    }
    
    public void close(){
       try{
           db.close();
       }catch(IOException e){
           e.printStackTrace();
       }
    }
    //[username:id:attribute] [attribute_value]
    public void putValuesToUser(String key, String value){
       
    }
 
    public DB getDB(){
        return db;
    }
    public void createCommit(String key,JSONObject item){
         db.put(bytes(key), bytes(item.toString()));  
    }
}
