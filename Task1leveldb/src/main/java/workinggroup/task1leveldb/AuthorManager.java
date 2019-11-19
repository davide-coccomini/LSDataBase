package workinggroup.task1leveldb;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.iq80.leveldb.DBIterator;
import static org.iq80.leveldb.impl.Iq80DBFactory.asString;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import org.json.JSONArray;
import org.json.JSONObject;
import workinggroup.task1.Obj.Author;

public class AuthorManager{
  
    DatabaseManager dbmanager;

    public AuthorManager(DatabaseManager parentManager) {
        this.dbmanager = parentManager;
    }
    
    /* Returns the id that will be used for the next author in the DB*/
    public String getNextAuthorId(){  
        return "author-" + dbmanager.getNextId();   
    }
 
    /* Creates a new author and puts it into the DB as a JSONObject*/
    public void create(String firstName, String lastName, String biography){
        System.out.println("creating author " + firstName + " " + lastName);
        
        dbmanager.open();
            JSONObject item = new JSONObject();
            item.put("idAUTHOR", dbmanager.getNextId());
            item.put("firstName", firstName);
            item.put("lastName", lastName);
            item.put("biography", biography);

            dbmanager.createCommit(getNextAuthorId(), item);
        dbmanager.close();
    }
     
    /* Finds an author by id */
    public Author read(int authorId){  
        System.out.println("looking for author with id " + authorId);
        Author a = new Author();
 
        try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
            keyIterator.seekToFirst();

            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
                String[] splittedString = key.split("-");

                if(!"author".equals(splittedString[0])){
                    keyIterator.next();
                    continue;
                }

                String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
                JSONObject jauthor = new JSONObject(resultAttribute);

                if(jauthor.getInt("idAUTHOR") == authorId){
                    a = new Author(jauthor);
                    break;
                }
                keyIterator.next();                
            }
        } catch(Exception ex){
            ex.printStackTrace();
        } 
          
        return a;
    }
    
  /* Deletes the author with given id and all the books written by him/her */
    public void delete(int authorId){  
        System.out.println("deleting author " + authorId);
        dbmanager.open();
        try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
            keyIterator.seekToFirst();

            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
                String[] splittedString = key.split("-");
                System.out.println(splittedString[0]);
                if("publisher".equals(splittedString[0])){ // There is no relationship between author and publisher
                    keyIterator.next();
                    continue;
                }

                String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
             
                if("author".equals(splittedString[0])){ // Delete author      
                    JSONObject jauthor = new JSONObject(resultAttribute);
                    if(jauthor.getInt("idAUTHOR") == authorId){
                        dbmanager.getDB().delete(bytes(key));
                    }
                } else { // Deletes all books referring to the author
                    JSONObject jbook = new JSONObject(resultAttribute);  
                    JSONArray jauthors  = jbook.getJSONArray("authors");
                    for(int i=0; i<jauthors.length(); i++){

                        int currentId = jauthors.getInt(i);
                        if(currentId == authorId){ // To be deleted
                            dbmanager.close();
                            dbmanager.getBmanager().delete(jbook.getInt("idBOOK"));
                            dbmanager.open();
                            break;
                        }
                    }
                }
                keyIterator.next();   
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
        dbmanager.close();
    }
    
    /* Selects all authors from the DB and returns the result as an observable list. 
    Called by "submit_Button" in UICOntroller.java */
    public ObservableList<Object> selectAllAuthors(){ 
        System.out.println("Printing all authors...");
        dbmanager.open();
            ObservableList<Object> result = FXCollections.observableArrayList();

            try {
                try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
                    keyIterator.seekToFirst();

                    while (keyIterator.hasNext()) {
                        String key = asString(keyIterator.peekNext().getKey());
                        System.out.println("key");
                        System.out.println(key);
                        String[] splittedString = key.split("-");

                        dbmanager.incrementNextId(Integer.parseInt(splittedString[1]));

                        if(!"author".equals(splittedString[0])){       
                            keyIterator.next();
                            continue;
                        }

                        String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
                        JSONObject jauthor = new JSONObject(resultAttribute);

                        Author author = new Author(jauthor);
                        result.add(author);
                        keyIterator.next();
                    }
                }
            } catch(IOException e){
               e.printStackTrace();
            }
        dbmanager.close();
        return result;
    } 
}
