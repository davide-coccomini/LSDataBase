package workinggroup.task0;

import java.sql.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseManager {
  
    private final ConnectionManager connManager;
    
    private static final String BOOK_TABLE = "Book";
    private static final String PUBLISHER_TABLE = "Publisher";
    private static final String AUTHOR_TABLE = "Author";
        
    public static final String SELECT_ALL_BOOKS = "SELECT * FROM  " + BOOK_TABLE;
    public static final String SELECT_ALL_PUBLISHERS = "SELECT * FROM " + PUBLISHER_TABLE;
    public static final String SELECT_ALL_AUTHORS = "SELECT * FROM  " + AUTHOR_TABLE;
    
    public static final String UPDATE_BOOK = "UPDATE " + BOOK_TABLE + " SET quantity = ? WHERE idBOOK = ?";
    public static final String SELECT_ALL_BOOKS_BY_AUTHOR = "SELECT B.title, A.firstName, A.lastName  FROM " + BOOK_TABLE + " B NATURAL JOIN "
            + AUTHOR_TABLE + " A WHERE A.idAUTHOR = ?";
    public static final String SELECT_ALL_BOOKS_BY_PUBLISHER = "SELECT B.title, P.name  FROM " + BOOK_TABLE + " B NATURAL JOIN "
            + PUBLISHER_TABLE + " P WHERE P.idPUBLISHER = ?";

    public static final String INSERT_BOOK = "INSERT INTO " + BOOK_TABLE + "(title,numPages,quantity,category,price,publicationYear,AUTHOR_idAUTHOR,PUBLISHER_idPUBLISHER) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_BOOK = "DELETE FROM " + BOOK_TABLE + " WHERE idBOOK = ?";  
    
    
    /* Handles commands inserted by the user via gui and launches the respective routine */
    public ObservableList<Object> commandManager(String cmd, Object[] args){  //forse non serve
      
         ArrayList<Object> result=null; // 
            
        try {
            connManager.connectionStart();
             
            switch(cmd){
                case "SELECT_ALL_BOOKS": 
                    result = connManager.worker(SELECT_ALL_BOOKS, null);
                break;
                case "SELECT_ALL_PUBLISHERS": 
                    result = connManager.worker(SELECT_ALL_PUBLISHERS, null);
                break;
                case "SELECT_ALL_AUTHORS": 
                    result = connManager.worker(SELECT_ALL_AUTHORS, null);
                break;
                case "UPDATE_BOOK":
                    result = connManager.worker(UPDATE_BOOK, args);
                    break;
                case "INSERT_BOOK":
                    result = connManager.worker(INSERT_BOOK, args);
                    break;
                case "DELETE_BOOK":
                    result = connManager.worker(DELETE_BOOK, args);
                    break;
                default: 
                    System.out.println("Wrong command");
                }
            connManager.connectionClose();
         
            
        } catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("SQLErrorCode: " + ex.getErrorCode());
        }
        
         return extractResult(result);
    }
    
   /* public void insertBook(String args){
        //gli argomenti vanno presi dalla tabella 
    }*/
    // Class initialization
    public DatabaseManager(){
        connManager = new ConnectionManager(); 
    }
    
    /* Given ArrayList as result of a query, move the data to front-end*/
    public ObservableList<Object> extractResult(ArrayList<Object> result){       
        if(result == null) {
            return null;
        }  
        System.out.println("arrL:   " +result.toString());
        ObservableList<Object> data = FXCollections.observableArrayList();
        data.addAll(result);
        System.out.println("obsL    "+data.toString());
        return data;
    }
    
    
}
