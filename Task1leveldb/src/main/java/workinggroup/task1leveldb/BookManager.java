package workinggroup.task1leveldb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.iq80.leveldb.DBIterator;
import static org.iq80.leveldb.impl.Iq80DBFactory.asString;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import org.json.JSONArray;
import org.json.JSONObject;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;

public class BookManager{
    
    DatabaseManager dbmanager;

    public BookManager(DatabaseManager parentManager) {
        this.dbmanager = parentManager;
    }
    
   
    public String getNextBookId(){  
     return "book-"+dbmanager.getNextId();   
    }
    
    public void create(String title, String numPages, String quantity, String category, String price, String publicationYear, String[] authorsId, String publisherId) {
        
        System.out.println("create book()");
        dbmanager.open();
            
            JSONObject item = new JSONObject();
            item.put("idBOOK", dbmanager.getNextId());
            item.put("title", title);
            item.put("numPages", numPages);
            item.put("quantity", quantity);
            item.put("category", category);
            item.put("price", price);
            item.put("publicationYear", publicationYear);
            item.put("authors", authorsId); 
            item.put("publisher", publisherId);

            dbmanager.createCommit(getNextBookId(),item);
            
        dbmanager.close();
    }
    public ObservableList<Object> selectAllBooks(){
        System.out.println("Selectallbooks()");
        dbmanager.open();
            ObservableList<Object> books = FXCollections.observableArrayList();

            try{
                try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
                    keyIterator.seekToFirst();
                    while (keyIterator.hasNext()) {


                        String key = asString(keyIterator.peekNext().getKey());
                        String[] splittedString = key.split("-");

                        dbmanager.incrementNextId(Integer.parseInt(splittedString[1]));

                        if(!"book".equals(splittedString[0])){
                            keyIterator.next();
                            continue;
                        }
                        String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
                        JSONObject jbook = new JSONObject(resultAttribute);

                        Book book = new Book(jbook);
                        
                        book.setPublisher(dbmanager.getPmanager().read(jbook.getInt("publisher")));
                        
                         
                        JSONArray jauthors  = jbook.getJSONArray("authors");
                        List<Author> authors = new ArrayList();
                        System.out.println(jauthors);
                        for(int i=0; i<jauthors.length(); i++){
                            
                            int a=-1;
                            a=jauthors.getInt(i);
                            if(a>=0){ 
                                authors.add(dbmanager.getAmanager().read(a));
                                
                                }
                            else{
                                System.out.println("a book has mysterious author");
                                
                            }
                          
                        }
                        
                      
                        book.setAuthors(authors);
                        
                        books.add(book);

                        keyIterator.next();
                    }
                }
            }

            catch(IOException e){
               e.printStackTrace();
            }
        dbmanager.close();
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
