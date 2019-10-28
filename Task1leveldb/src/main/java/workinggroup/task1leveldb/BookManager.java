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
    
    private String nextId;
    
    public String getNextId(){ // TODO: Generare adeguatamente questa chiave (magari iterare su tutti i libri alla ricerca dell'id più alto e incrementarlo di 1)
     return "book-0";   
    }
    
    public void create(String title, String numPages, String quantity, String category, String price, String publicationYear, String[] authorsId, String publisherId) {
        
        System.out.println("create book()");
        
        JSONObject item = new JSONObject();
        item.put("idBOOK", 0);
        item.put("title", title);
        item.put("numPages", numPages);
        item.put("quantity", quantity);
        item.put("category", category);
        item.put("price", price);
        item.put("publicationYear", publicationYear);
        item.put("authors", authorsId);
        item.put("publishers", publisherId);
        
        super.createCommit(getNextId(),item);
        
        this.close();
    }
    public ObservableList<Object> selectAllBooks(){
        System.out.println("Selectallbooks()");
 
        ObservableList<Object> books = FXCollections.observableArrayList();
  
        try{
            try (DBIterator keyIterator = super.getDB().iterator()) {
                keyIterator.seekToFirst();
                while (keyIterator.hasNext()) {
                    String key = asString(keyIterator.peekNext().getKey());
                    String[] splittedString = key.split("-");
                    if(!"book".equals(splittedString[0])){
                        keyIterator.next();
                        continue;
                    }
                    String resultAttribute = asString(super.getDB().get(bytes(key)));
                    JSONObject jbook = new JSONObject(resultAttribute);
                    
                    Book book = new Book(jbook);
                    books.add(book);
                    keyIterator.next();
                }
            }
        }
        
        catch(IOException e){
           e.printStackTrace();
        }
        this.close();
        return books;
    } 
    public Book read(int bookId){ // TODO: Aggiornare questa funzione
        
        Book b = null;
        try{
         
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
           
        }
        return b;
    }
    public void update(int bookId, int quantity){ // TODO: Aggiornare questa funzione
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
    }
    public void delete(int bookId){ // TODO: Aggiornare questa funzione
        try{
         
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
          
        }
    }


}
