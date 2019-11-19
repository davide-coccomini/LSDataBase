package workinggroup.task1.Obj;

import org.json.JSONObject;

public class Author{
       
    private int idAUTHOR;
    private String firstName;
    private String lastName;
    private String biography;
            
    public Author(String firstName, String lastName, String biography){
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }

    public Author() {
        
    }
    
    public Author(JSONObject jauthor) {
        this.idAUTHOR = (int)jauthor.get("idAUTHOR");
        this.firstName = (String) jauthor.get("firstName");
        this.lastName = (String) jauthor.get("lastName");
        this.biography = (String) jauthor.get("biography");
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
