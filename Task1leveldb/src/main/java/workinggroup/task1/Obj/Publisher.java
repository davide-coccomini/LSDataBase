package workinggroup.task1.Obj;

import java.util.List;


public class Publisher{
    private int idPUBLISHER;

    private String name;
    private String location;
    private List<Book> books;

    public Publisher() {
    }
    
    public Publisher(String name, String location){
        this.name = name;
        this.location = location; 
    }

    public int getIdPUBLISHER() {
        return idPUBLISHER;
    }

    public void setIdPUBLISHER(int id) {
        this.idPUBLISHER = id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getLocation() {
        return location;
    }
   
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
