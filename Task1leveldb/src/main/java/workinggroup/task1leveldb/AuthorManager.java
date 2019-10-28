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


public class AuthorManager extends DatabaseManager{
     
    private int nextId;
    
    public String getNextId(){ // TODO: Generare adeguatamente questa chiave (magari iterare su tutti gli autori alla ricerca dell'id più alto e incrementarlo di 1)
        return "author-0";   
    }
 
     public void create(String firstName, String lastName, String biography){
        JSONObject item = new JSONObject();
        item.put("idAUTHOR", 0);
        item.put("firstName", firstName);
        item.put("lastName", lastName);
        item.put("biography", biography);

        
        super.createCommit(getNextId(),item);
        this.close();
    }
    public Author read(int authorId){ // TODO: Aggiornare questa funzione
        Author a = new Author();
        try{
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            
        }
        return a;
    }
    public void delete(int authorId){ // TODO: Aggiornare questa funzione
        try{
        
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            
        }
    }
 public ObservableList<Object> selectAllAuthors(){ 
        System.out.println("Selectallauthors()");
 
        ObservableList<Object> result = FXCollections.observableArrayList();
  
        try{
            try (DBIterator keyIterator = super.getDB().iterator()) {
                keyIterator.seekToFirst();
                
                while (keyIterator.hasNext()) {
                    String key = asString(keyIterator.peekNext().getKey());
                    System.out.println("key");
                    System.out.println(key);
                    String[] splittedString = key.split("-");
                    if(!"author".equals(splittedString[0])){       
                        keyIterator.next();
                        continue;
                    }
                    
                    String resultAttribute = asString(super.getDB().get(bytes(key)));
                    JSONObject jauthor = new JSONObject(resultAttribute);
                    
                    Author author = new Author(jauthor);
                    result.add(author);
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
