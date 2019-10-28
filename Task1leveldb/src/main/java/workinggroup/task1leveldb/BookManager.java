package workinggroup.task1leveldb;

import java.io.IOException;
import static java.lang.Long.parseLong;
import java.util.ArrayList;
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

public class BookManager extends DatabaseManager{
    
    private int nextId;
    
    public int getNextId(){
     return nextId;   
    }
 
    public void create(String title, String numPages, String quantity, String category, String price, String publicationYear, String[] authorsId, String publisherId) {
        
        System.out.println("create book()");
        
        JSONObject item = new JSONObject();
        item.put("title", title);
        item.put("numPages", numPages);
        item.put("quantity", quantity);
        item.put("category", category);
        item.put("price", price);
        item.put("publicationYear", publicationYear);
 
//    
//        AuthorManager am = new AuthorManager();
//        am.setup();
//        for(int i=0; i<authorsId.length; i++){
//            Author a = am.read(Integer.parseInt(authorsId[i]));
//            authors.add(a);
//        }
//        
//        b.setAuthors(authors);
//        PublisherManager pm = new PublisherManager();
//        pm.setup();
//        Publisher p = pm.read(Integer.parseInt(publisherId));
//        b.setPublisher(p);
        
        super.createCommit(getNextId(),item);
    }
    public ObservableList<Object> selectAllBooks(){
        System.out.println("Selectallbooks()");
 
        ObservableList<Object> result = FXCollections.observableArrayList();
  
        try{
            DBIterator keyIterator = super.getDB().iterator();           
            keyIterator.seekToFirst();
            
            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
               
               
                String resultAttribute = new String(key + ":" + asString(super.getDB().get(bytes(key))));
                
                JSONObject jbook = new JSONObject(resultAttribute);
                
                Book book = new Book(jbook);
                result.add(book);
                keyIterator.next();
            }
            keyIterator.close();
        }
        
        catch(IOException e){
           e.printStackTrace();
        }
   
        return result;
    } 
    public Book read(int bookId){
        
        Book b = null;
        try{
         
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
           
        }
        return b;
    }
    public void update(int bookId, int quantity){
        Book book = new Book();
        Book book_original = new Book();
        book_original = read(bookId);
        
        book.setIdBOOK(bookId);
        book.setQuantity(quantity);
        
        book.setTitle(book_original.getTitle());
        book.setNumPages(book_original.getNumPages());
        book.setCategory(book_original.getCategory());
        book.setPrice(book_original.getPrice());
        book.setPublicationYear(book_original.getPublicationYear());
        book.setAuthors(book_original.getAuthors());
        book.setPublisher(book_original.getPublisher());
        
        try {
           
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
           
        }
        // TODO: Update table
    }
    public void delete(int bookId){
        try{
         
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
          
        }
    }


}
