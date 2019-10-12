package workinggroup.task0;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    public ArrayList <Object> worker(String query, Object[] args) throws SQLException{
        if(args == null){
            Statement stmt = conn.createStatement();
            stmt.execute(query);                                    
            ResultSet rs = stmt.getResultSet();  
      
            ArrayList<Object> result = new ArrayList<>();
            Object o;

            while(rs.next()){
                o = chooseType(query, rs);
                result.add(o);
                System.out.print("line :  ");
                System.out.println(result); 
            }

            rs.close();
            stmt.close();
            return result; 
        }else{
            PreparedStatement ps = conn.prepareStatement(query);
            for(int i=0; i<args.length; i++){
                ps.setObject(i + 1,args[i]);
            }
            ps.executeUpdate();
            return null;
        }
    }
}
