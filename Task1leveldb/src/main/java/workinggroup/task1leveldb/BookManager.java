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
    
    /* Returns the id that will be used for the next book in the DB*/
    public String getNextBookId(){  
     return "book-" + dbmanager.getNextId();   
    }
    
    /* Creates a new book and puts it into the DB as a JSONObject*/
    public void create(String title, String numPages, String quantity, String category, String price, String publicationYear, String[] authorsId, String publisherId) {
        
        System.out.println("creating book " + title + " by "+ authorsId[0] + " published by " + publisherId);
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
    
    /* Displays a all the books using an observableList*/
    public ObservableList<Object> selectAllBooks(){
        System.out.println("selectallbooks()");
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
                        for(int i=0; i<jauthors.length(); i++){
                            
                            int a=-1;
                            a=jauthors.getInt(i);
                            if(a>=0){ 
                                authors.add(dbmanager.getAmanager().read(a));
                            }else{
                                System.out.println("A book has mysterious author");   
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
    
    /* Reads a book with give id from the DB */
    public Book read(int bookId){
        System.out.println("looking for Book with id " + bookId);
        Book b = new Book();
 
        try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
            keyIterator.seekToFirst();

            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
                String[] splittedString = key.split("-");

                if(!"book".equals(splittedString[0])){
                    keyIterator.next();
                    continue;
                }

                String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
                JSONObject jbook = new JSONObject(resultAttribute);

                if(jbook.getInt("idBOOK") == bookId){
                    b = new Book(jbook);
                    break;
                }
                keyIterator.next();   
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        } 
        return b;
    }
    /* Reads a book with given id as a JSONObject */
    public JSONObject readJSON(int bookId){
        System.out.println("looking for Book JSON with id " + bookId);
        dbmanager.open();
        JSONObject jbook = new JSONObject();
        try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
            keyIterator.seekToFirst();

            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
                String[] splittedString = key.split("-");

                if(!"book".equals(splittedString[0])){
                    keyIterator.next();
                    continue;
                }

                String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
                jbook = new JSONObject(resultAttribute);

                if(jbook.getInt("idBOOK") == bookId){              
                    break;
                }
                keyIterator.next();      
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        } 
        dbmanager.close();
        return jbook;
    }
    /* Updates the quantity of a book */
    public void update(int bookId, int quantity){ 
        JSONObject jbook = dbmanager.getBmanager().readJSON(bookId);
        dbmanager.getBmanager().delete(bookId);
        jbook.remove("quantity");
        jbook.put("quantity", Integer.toString(quantity));
        dbmanager.open();
        dbmanager.createCommit(getNextBookId(),jbook);
        dbmanager.close();
        
    }
    /* Deletes a book */
    public void delete(int bookId){ 
        dbmanager.open();
        try (DBIterator keyIterator = dbmanager.getDB().iterator()) {
            keyIterator.seekToFirst();

            while (keyIterator.hasNext()) {
                String key = asString(keyIterator.peekNext().getKey());
                String[] splittedString = key.split("-");

                if(!"book".equals(splittedString[0])){
                    keyIterator.next();
                    continue;
                }

                String resultAttribute = asString(dbmanager.getDB().get(bytes(key)));
                JSONObject jauthor = new JSONObject(resultAttribute);

                if(jauthor.getInt("idBOOK")==bookId){
                    
                    dbmanager.getDB().delete(bytes(key));
                    break;
                }
                keyIterator.next();
                    
                }
            }
        catch(Exception ex){
            ex.printStackTrace();
        } 
        dbmanager.close();
    }


}
