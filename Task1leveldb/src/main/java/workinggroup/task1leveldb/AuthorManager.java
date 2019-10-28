package workinggroup.task1leveldb;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;


public class AuthorManager extends DatabaseManager{
     
    private int nextId;
    
    public int getNextId(){
        return nextId;   
    }
 
     public void create(String firstName, String lastName, String biography, List<Book> books){
        JSONObject item = new JSONObject();
        
        item.put("firstName", firstName);
        item.put("lastName", lastName);

        
        super.createCommit(getNextId(),item);
    }
    public Author read(int authorId){
        Author a = new Author();
        try{
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            
        }
        return a;
    }
    public void delete(int authorId){
        try{
        
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            
        }
    }
    public ObservableList<Object> selectAllAuthors(){
        System.out.println("Selectallauthors()");
       
        String query = "SELECT a FROM Author a";
       
        ObservableList<Object> authors = null;
        try{
           
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            
        }
        
        
        return authors;
    } 
}
