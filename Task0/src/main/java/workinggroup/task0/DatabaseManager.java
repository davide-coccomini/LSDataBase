package workinggroup.task0;

import java.sql.*;
import java.util.logging.*;

import java.util.Scanner;   //togliere dopo aver fatto input grafico

public class DatabaseManager {
  
    private final ConnectionManager connManager;
    
    private static final String BOOK_TABLE = "Book";
    private static final String PUBLISHER_TABLE = "Publisher";
    private static final String AUTHOR_TABLE = "Author";
        
    public static final String SELECT_ALL_BOOKS = "SELECT * FROM  " + BOOK_TABLE;
    public static final String SELECT_ALL_PUBLISHERS = "SELECT * FROM " + PUBLISHER_TABLE;
    public static final String SELECT_ALL_AUTHORS = "SELECT * FROM  " + AUTHOR_TABLE;
    
    public static final String UPDATE_BOOK = "UPDATE " + BOOK_TABLE + "SET quantity = ";
    public static final String SELECT_ALL_BOOKS_BY_AUTHOR = "SELECT title, firstName, lastName  FROM" + BOOK_TABLE + " INNER JOIN "
            + AUTHOR_TABLE + " ON idAUTHOR = ?";
    public static final String SELECT_ALL_BOOKS_BY_PUBLISHER = "SELECT title, firstName, lastName  FROM" + BOOK_TABLE + " INNER JOIN "
            + AUTHOR_TABLE + " ON idPUBLISHER = ?";
    public static final String INSERT_BOOK = "INSERT INTO" + BOOK_TABLE + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_BOOK = "DELETE FROM " + BOOK_TABLE + " WHERE id = ?";  
    
    
    /* Handles commands inserted by the user via gui and launches the respective routine */
    public void commandManager(ConnectionManager connManager, String cmd, Object[] args) throws SQLException{  //forse non serve
        switch(cmd){
            case SELECT_ALL_BOOKS: 
                connManager.worker(SELECT_ALL_BOOKS, null);
            break;
            case SELECT_ALL_PUBLISHERS: 
                connManager.worker(SELECT_ALL_PUBLISHERS, null);
            break;
            case SELECT_ALL_AUTHORS: 
                connManager.worker(SELECT_ALL_AUTHORS, null);
            break;
            case INSERT_BOOK:
                connManager.worker(INSERT_BOOK, args);
                break;
            case DELETE_BOOK:
                connManager.worker(DELETE_BOOK, args);
                break;
            default: 
                System.out.println("Wrong command");
        }
    }
    
   /* public void insertBook(String args){
        //gli argomenti vanno presi dalla tabella 
    }*/
    // Class initialization
    public DatabaseManager(){
        connManager = new ConnectionManager(); 
    }
    /* Connects to the database, handles commands and closes the connection */
    public void doStuff(){
        /* TODO aggiornare col manager */
        Scanner sc = new Scanner(System.in);    //poi input grafico
        String query = sc.nextLine();           //poi input grafico
        try {
            connManager.connectionStart();
            //commandManager(connManager, query);
            connManager.worker(query,null);
            connManager.connectionClose();
        } catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("SQLErrorCode: " + ex.getErrorCode());
        }
        
    }
    /* */
    public void extractResult(){
        
    }
    
    
}
