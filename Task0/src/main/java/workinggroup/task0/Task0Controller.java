package workinggroup.task0;
 
import java.sql.Date;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import static workinggroup.task0.DatabaseManager.DELETE_BOOK;
import static workinggroup.task0.DatabaseManager.INSERT_BOOK;
import static workinggroup.task0.DatabaseManager.SELECT_ALL_AUTHORS;
import static workinggroup.task0.DatabaseManager.SELECT_ALL_BOOKS;
import static workinggroup.task0.DatabaseManager.SELECT_ALL_BOOKS_BY_AUTHOR;
import static workinggroup.task0.DatabaseManager.SELECT_ALL_BOOKS_BY_PUBLISHER;
import static workinggroup.task0.DatabaseManager.SELECT_ALL_PUBLISHERS;
import static workinggroup.task0.DatabaseManager.UPDATE_BOOK;
 

public class Task0Controller
{
    private ObservableList<Object> content;
    private DatabaseManager db_Manager;

    @FXML private TableView<Object> tableView;
    
    @FXML public Button searchButton;
         
    /* initialization for the main table. */
    public void init_Table() {

        //tableView.setEditable(true); 
        content = null;
        db_Manager = new DatabaseManager();
    }
    /* event triggered by user click*/
    public void submit_Button(){
         
        Scanner sc = new Scanner(System.in);    //poi input grafico
        String query = sc.nextLine();           //poi input grafico
     
        // TODO: read input
        
        content = db_Manager.doStuff(query);
        
        if(content.isEmpty()==false){
            tableView.setItems(content);
            
            format_Table(query);
        }
    }
    /* create table columns according to the results expected */
    private void format_Table(String query){
        switch(query){
            case SELECT_ALL_BOOKS: 
            case UPDATE_BOOK:
            case INSERT_BOOK:
            case DELETE_BOOK:
                format_Book(); 
                break;
            case SELECT_ALL_BOOKS_BY_AUTHOR:
                format_Author();
                format_Book();
                break;
            case SELECT_ALL_BOOKS_BY_PUBLISHER:
                format_Publisher();
                format_Book();
                break;
            case SELECT_ALL_PUBLISHERS:
                format_Publisher();
                break;
            case SELECT_ALL_AUTHORS:
                format_Author();
                break;
       
        }
    }
    /* add columns to main table to display a Book */
    private void format_Book(){        
        TableColumn<Object,String> title_Col;
        title_Col = new TableColumn<>("Book Title");
        title_Col.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn<Object,Double> price_Col;
        price_Col = new TableColumn<>("Book Price");
        price_Col.setCellValueFactory(new PropertyValueFactory("price"));
        TableColumn<Object,Integer> pages_Col;
        pages_Col = new TableColumn<>("Book Pages");
        pages_Col.setCellValueFactory(new PropertyValueFactory("numPages"));
        TableColumn<Object,Date> date_Col;
        date_Col = new TableColumn<>("Date");
        date_Col.setCellValueFactory(new PropertyValueFactory("date"));
        TableColumn<Object,String> cat_Col;
        cat_Col = new TableColumn<>("Book Category");
        cat_Col.setCellValueFactory(new PropertyValueFactory("category"));
        TableColumn<Object,Integer> q_Col;
        q_Col = new TableColumn<>("Copies In Stock");
        q_Col.setCellValueFactory(new PropertyValueFactory("quantity"));             
         
        tableView.getColumns().setAll(title_Col, price_Col, pages_Col, date_Col, cat_Col, q_Col);
    }
    /* add columns to main table to display an author */
    private void format_Author(){   
        TableColumn<Object,String> fn_Col;
        fn_Col = new TableColumn<>("Author Name");
        fn_Col.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Object,String> ln_Col;
        ln_Col = new TableColumn<>("Author Surname");
        ln_Col.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Object,String> bio_Col;
        bio_Col = new TableColumn<>("Author's Bio");
        bio_Col.setCellValueFactory(new PropertyValueFactory("biography"));
  
         tableView.getColumns().setAll(fn_Col,ln_Col,bio_Col);
    }
     /* add columns to main table to display a publisher */
    private void format_Publisher(){   
        TableColumn<Object,String> pub_Col;
        pub_Col = new TableColumn<>("Publisher Name");
        pub_Col.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Object,String> loc_Col;
        loc_Col = new TableColumn<>("Publisher Location");
        loc_Col.setCellValueFactory(new PropertyValueFactory("location"));
        
        tableView.getColumns().setAll(pub_Col,loc_Col);
    }
   
    /* reset the table */
    public void table_Clear(){
        content.clear();
        /* TODO: flush the table displayed on fxml */
    }
  
}
