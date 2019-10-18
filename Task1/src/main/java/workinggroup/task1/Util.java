package workinggroup.task1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.*;
import static workinggroup.task1.DatabaseManager.*;
import workinggroup.task1.Obj.*;

public class Util {
    
    public ArrayList formatResult(){    //per la funzione di inserimento di un nuovo libro
        ArrayList <Object> a = new ArrayList<>();
        return a;
    }
    
    /* */
    public static Object chooseType(String query, ResultSet rs){   
        Object o = null;
        switch(query){
            case SELECT_ALL_BOOKS: 
                try {
                    o = extractBook(rs); 
                } catch (SQLException ex) {
                    Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                }
            break;
            case SELECT_ALL_AUTHORS: 
                try {
                    o = extractAuthor(rs);
                } catch (SQLException ex) {
                    Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                }
            break;
            case SELECT_ALL_PUBLISHERS: 
                try {
                    o = extractPublisher(rs);  
                } catch (SQLException ex) {
                    Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                }
            break;
        }
        return o;
    }
    
    public static Book extractBook(ResultSet rs) throws SQLException{ 
        Book o;
        o = new Book(rs.getInt("idBOOK"),
                    rs.getString("title"),
                    rs.getDouble("price"),
                    rs.getInt("numPages"),
                    rs.getInt("PublicationYear"),
                    rs.getString("category"),
                    rs.getInt("quantity")      
                );
        return o;
    }
    public static Author extractAuthor(ResultSet rs) throws SQLException{ 
        Author o;
        o = new Author(rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("biography")
                );
        return o;
    }
    public static Publisher extractPublisher(ResultSet rs) throws SQLException{ 
        Publisher o;
        o = new Publisher(rs.getString("name"),
                    rs.getString("location")
                );
        return o;
    }
    
}
