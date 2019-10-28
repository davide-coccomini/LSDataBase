
package workinggroup.task1.Obj;

import java.util.List;

public class Author{
       
    private int idAUTHOR;
    private String firstName;
    private String lastName;
    private String biography;
    List<Book> books;
            
    public Author(String firstName, String lastName, String biography, List<Book> books){
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.books = books;
    }

    public Author() {
        
    }

    public int getIdAUTHOR() {
        return idAUTHOR;
    }

    public void setIdAUTHOR(int id) {
        this.idAUTHOR = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBiography() {
        return biography;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    

    
}
