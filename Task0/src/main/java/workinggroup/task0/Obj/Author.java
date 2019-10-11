
package workinggroup.task0.Obj;


public class Author{
    private String firstName;
    private String lastName;
    private String biography;
            
    public Author(String firstName, String lastName, String biography){
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
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
}
