package workinggroup.task1.Obj;

import java.util.List;
import org.json.JSONObject;


public class Publisher{
    private int idPUBLISHER;

    private String name;
    private String location;

    public Publisher() {
    }
    
    public Publisher(String name, String location){
        this.name = name;
        this.location = location; 
    }
    public Publisher(JSONObject jpublisher) {
        this.idPUBLISHER = (int) jpublisher.get("idPUBLISHER");
        this.name = (String) jpublisher.get("name");
        this.location = (String) jpublisher.get("location");
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
   
    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
