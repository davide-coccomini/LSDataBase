package workinggroup.task0;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.*;
import java.util.logging.*;
import static workinggroup.task0.DatabaseManager.*;
import workinggroup.task0.Obj.*;

public class Util {
    
    public ArrayList formatResult(){    //per la funzione di inserimento di un nuovo libro
        ArrayList <Object> a = new ArrayList<>();
        //prendere i parametri inseriti dall'utente nell'ultima riga della tabella
        return a;
    }
    /* Converts a Date retrieved from user's input to a LocalDate*/
    public static LocalDate formatDate(Date date){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDate ld = instant.atZone(defaultZoneId).toLocalDate();
        return ld;
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
            /*case INSERT:
                n = new Book(rs.getString(BookShop.Book.Title),
                        rs.getInt(BookShop.Book.ID)
                ); */
            /*default:
                return null;    //error*/
        }
        return o;
    }
    
    public static Book extractBook(ResultSet rs) throws SQLException{ 
        Book o;
        o = new Book(rs.getString("title"),
                    rs.getDouble("price"),
                    rs.getInt("numPages"),
                    formatDate(rs.getDate("date")),
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
