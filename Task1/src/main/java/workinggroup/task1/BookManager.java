package workinggroup.task1;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class BookManager {
    private final JpaManager jmanager;
    private final AuthorManager amanager;
    private final PublisherManager pmanager;
    
    public BookManager(JpaManager j, AuthorManager a, PublisherManager p){
        amanager = a;
        pmanager = p;
        jmanager = j;
        
    }
    /* Creates a new Book and puts it into the DB */
    public void create(String title, String numPages, String quantity, String category, String price, String publicationYear, String[] authorsId, String publisherId) {
        System.out.println("creating book: " + title + " written by " + authorsId[0] + " published by " + publisherId);
        /* checking if the book is already in the DB to avoid duplications
        if author, publisher, edition and quantity are the same nothing changes;
        if the edition is different a new book with different edition is added;
        if the quantity is different the book is updated */
        List<Book> list = findByTitle(title);
        if(list != null){
            for (Book item : list) {
                // Checking if the first author and the publisher are the same
                if( Integer.toString(item.getAuthors().get(0).getIdAUTHOR()).equals(authorsId[0]) &&
                    Integer.toString(item.getPublisher().getIdPUBLISHER()).equals(publisherId) ) {
                    // Checking if it's a new edition
                    if(Integer.toString(item.getPublicationYear()).equals(publicationYear)){    
                        // If the only difference is the quantity in stock it needs to be updated
                        if(!Integer.toString(item.getQuantity()).equals(quantity)) {
                            update(item.getIdBOOK(), Integer.parseInt(quantity));
                            System.out.println("Quantity updated");
                            return;
                        } else {
                            System.out.println("The book is already in the database!");
                            return;
                        }
                    } else {
                        System.out.println("New edition added");
                    }
                }      
            }
        }
        Book b = new Book();
        b.setTitle(title);
        b.setNumPages(Integer.parseInt(numPages));
        b.setQuantity(Integer.parseInt(quantity));
        b.setCategory(category);
        b.setPrice(Double.parseDouble(price));
        b.setPublicationYear(Integer.parseInt(publicationYear));
        
        List<Author> authors = new ArrayList<>();       //a book can have more authors
        for (String authorsId1 : authorsId) {
            Author a = amanager.read(Integer.parseInt(authorsId1));
            authors.add(a);
        }
        b.setAuthors(authors);
  
        Publisher p = pmanager.read(Integer.parseInt(publisherId));
        b.setPublisher(p);
        
        jmanager.createCommit(b);                       //see JpaManager.java
    }
    /* Finds an author by id */
    public Book read(int bookId){
        return jmanager.findById(Book.class, bookId);
    }
    
    /* Finds a list of books with given title */
    public List<Book> findByTitle(final String title) {
        return jmanager.findListByStringField(Book.class,"title",title);
    }
    /* Updates the quantity in stock for the book with given id */
    public void update(int bookId, int quantity){
        Book book = new Book();
        Book book_original = read(bookId);
        
        book.setIdBOOK(bookId);
        book.setQuantity(quantity);
        
        book.setTitle(book_original.getTitle());
        book.setNumPages(book_original.getNumPages());
        book.setCategory(book_original.getCategory());
        book.setPrice(book_original.getPrice());
        book.setPublicationYear(book_original.getPublicationYear());
        book.setAuthors(book_original.getAuthors());
        book.setPublisher(book_original.getPublisher());
        
        jmanager.update(Book.class, book);
        
    }
    /* Deletes the book with given id */
    public void delete(int bookId){
       jmanager.deleteById(Book.class, bookId);
    }
    /* Selects all books from the DB and returns the result as an observable list. 
    Called by "submit_Button" in UICOntroller.java */
    public ObservableList<Object> selectAllBooks(){
        return jmanager.selectAll(Book.class);
    } 

}
