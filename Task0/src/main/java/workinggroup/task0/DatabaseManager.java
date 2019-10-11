package workinggroup.task0;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lorenzo
 */
public class DatabaseManager {
  
    private ConnectionManager conn_Manager;
    
    private static final String BOOK_TABLE = "Book";
    private static final String PUBLISHER_TABLE = "Publisher";
    private static final String AUTHOR_TABLE = "Author";
    
    
    private static final String SELECT_ALL_BOOKS = "select * from  "+BOOK_TABLE;
    private static final String SELECT_ALL_PUBLISHERS = "select * from " + PUBLISHER_TABLE;
    private static final String SELECT_ALL_AUTHORS = "select * from  "+AUTHOR_TABLE;
    
    // class initialization
    public DatabaseManager(){
        conn_Manager = new ConnectionManager();
         
    }
    
    public void do_stuff(){
    
        /* TODO aggiornare col manager */
     try{
        conn_Manager.connection_Start();
        
        // TODO: switch
        conn_Manager.worker(SELECT_ALL_BOOKS);  
        
        conn_Manager.connection_Close();
     }
      catch(SQLException ex){
            System.out.println("SQLException: " +ex.getMessage());
            System.out.println("SQLState: " +ex.getSQLState());
            System.out.println("SQLErrorCode: " +ex.getErrorCode());
             }
        
    }
    
    public void extract_Result(){
        
    }
    
    
}
