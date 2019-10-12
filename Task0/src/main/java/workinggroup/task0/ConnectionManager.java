package workinggroup.task0;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static workinggroup.task0.Util.chooseType;

public class ConnectionManager {
    /*Parameters used to connect to the database "bookshop"*/
    private final String db_Address = "//localhost:3306/";
    private final String db_User = "root";
    private final String db_Password = "";
    private final String db_Name = "bookshop";
    private Connection conn;
  
    /* Concats all parameters into a String that will be used in the connection */
    private String makeStrString(){
        String str = "";
        // TODO: sistemare questa
        //str = "jdbc:mysql://localhost:3306/bookshop?user=root";
        str = "jdbc:mysql:" + db_Address + db_Name + "?user=" + db_User;
        return str;
    }
    
    /* Handles JDBC to open a connection with database */
    public boolean connectionStart() throws SQLException{
        String connStr = makeStrString();/* connessione, put code here */
        conn = DriverManager.getConnection(connStr);
        return true;  
    }
    /* Handles JDBC to close a connection */
    public boolean connectionClose() throws SQLException{
        conn.close();
        return true;
    }
    /* Creates an ArrayList of the Object that needs to be returned as a result of the query */
    public ArrayList <Object> worker(String query) throws SQLException{
        
        Statement stmt = conn.createStatement();
        stmt.execute(query);                                    
        ResultSet rs = stmt.getResultSet();   
        
        ArrayList<Object> result = new ArrayList<>();
        Object o;
                        
        while(rs.next()){
            /* TODO extract data here e mettere nella arraylist*/
            o = chooseType(query, rs);
            result.add(o);
            System.out.print("line :  ");
            //System.out.print(rs.getString("title"));
            System.out.println(result); //stampa tutto, dividere le righe
            //System.out.print("\n");
        }
             
        rs.close();
        stmt.close();
        //result.clear(); consigliabile svuotarla da qualche parte invece di aspettare il garbage collector?
        return result;  // TODO: ritornare la lista giusta 
    }
}
