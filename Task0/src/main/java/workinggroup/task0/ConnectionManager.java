package workinggroup.task0;
 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lorenzo
 */
 
public class ConnectionManager {
    
    private String db_Address="//localhost:3306/";
    private String db_User="root";
    private String db_Password="";
    private String db_Name="bookshop";
    private Connection conn;
  
    private String make_Str_String(){
        
        String str= "";
        // TODO: sistemare questa
       str = "jdbc:mysql://localhost:3306/bookshop?user=root";
      //  str = "jdbc:derby://localhost:1527/library?user=root";
       
        return str;
    }
    /* Handle JDBC to open a connection with database */
    public boolean connection_Start() throws SQLException{
        String connStr = make_Str_String();/* connessione, put code here */
         
        conn = DriverManager.getConnection(connStr);

        return true;  
    }
    /* Handle JDBC to close a connection */
    public boolean connection_Close() throws SQLException{
        
        conn.close();
        
        return true;
    }
    public ArrayList<Object> worker(String query) throws SQLException{
        
        Statement stmt = conn.createStatement();

        stmt.execute(query);
                                                  
        ResultSet rs = stmt.getResultSet();       
            
        while(rs.next()){
             /* TODO extract data here  e mettere nella arraylist*/
                 
            System.out.print("line :  ");
            System.out.print(rs.getString("title"));
            System.out.print("\n");
            }
             
        rs.close();
        stmt.close();
        
        return null;  // TODO: ritornare la lista giusta 
        }
}
