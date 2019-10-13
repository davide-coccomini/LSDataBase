package workinggroup.task0;
 


import java.sql.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import workinggroup.task0.Obj.Book;


public class Task0Controller
{
    private ObservableList<Object> content;
    private DatabaseManager db_Manager;

    @FXML private TableView<Object> tableView;
    @FXML private HBox mainBox;
    
        
    /* initialization for the main table. */
    public Task0Controller(HBox mb, TableView<Object> t) {

        mainBox = mb;
        tableView = t;
        content = null;
        db_Manager = new DatabaseManager();
        
    }
    /* event triggered by user click*/
    @FXML public void submit_Button(int button_Code){
     
        String query;          
         
        switch(button_Code){
            default:
            case 1:
                query = "SELECT_ALL_BOOKS";
                break;
            case 2:
                query = "SELECT_ALL_AUTHORS";
                break;
            case 3:
                query = "SELECT_ALL_PUBLISHERS";
                break;
        }
     
        content = db_Manager.commandManager(query,null);
        
        if((content!=null)&&(content.isEmpty()==false)){
         
            tableView.setItems(content);
            format_Table(query);
        }
        else{
            System.out.println("Query result is empty");
        }
    }
    /* create table columns according to the results expected */
    private void format_Table(String query){
        switch(query){
            case "SELECT_ALL_BOOKS": 
            case "UPDATE_BOOK":
            case "INSERT_BOOK":
            case "DELETE_BOOK":
                format_Book(); 
                break;
            case "SELECT_ALL_BOOKS_BY_AUTHOR":
                format_Author();
                format_Book();
                break;
            case "SELECT_ALL_BOOKS_BY_PUBLISHER":
                format_Publisher();
                format_Book();
                break;
            case "SELECT_ALL_PUBLISHERS":
                format_Publisher();
                break;
            case "SELECT_ALL_AUTHORS":
                format_Author();
                break;
       
        }
    }
    /* add columns to main table to display a Book */
    private void format_Book(){ 
        TableColumn<Object,Integer> id_Col;
        id_Col = new TableColumn<>("Book Id");
        id_Col.setCellValueFactory(new PropertyValueFactory("idBOOK"));
       // id_Col.setVisible(false);
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
        date_Col = new TableColumn<>("Publication Date");
        date_Col.setCellValueFactory(new PropertyValueFactory("publicationDate"));
        TableColumn<Object,String> cat_Col;
        cat_Col = new TableColumn<>("Book Category");
        cat_Col.setCellValueFactory(new PropertyValueFactory("category"));
        TableColumn<Object,Integer> q_Col;
        q_Col = new TableColumn<>("Copies In Stock");
        q_Col.setCellValueFactory(new PropertyValueFactory("quantity"));             
  
        tableView.getColumns().setAll(id_Col,title_Col, price_Col, pages_Col, date_Col, cat_Col, q_Col);
        
        generate_Edit_Buttons();
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
    /* make the whole column of edit buttons */
    private void generate_Edit_Buttons(){
        
        VBox vbox = new VBox();
     
        Book b;
        for(Object line:content) {
               
           b=(Book)line;
            
           HBox hbox = make_Edit_Buttons(b.getIdBOOK(),b.getQuantity());
       
           vbox.getChildren().add(hbox);
        }
        
        mainBox.getChildren().add(vbox);
    }
    /* prepare the buttons to edit books::quantity in each row*/
    private HBox make_Edit_Buttons(final int row_Id,final int quantity){
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
      //  hbox.SimpleStringProperty("row");
        
        
        
        Button button1 = new Button("+");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                quantity_Edit(row_Id,quantity+1);
                }
            });
            
        final TextField button2 = new TextField();
        button2.setEditable(true);
        button2.focusedProperty().addListener(new ChangeListener<Boolean>()
            {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                if (newPropertyValue)
                    { // on focus
                        
                    }
                else
                    { // out of focus
                        int val = Integer.parseInt(button2.getText());
                        quantity_Edit(row_Id,val);
                    }
                }
            });
        
        Button button3 = new Button("-");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                quantity_Edit(row_Id,quantity-1);
                }
            });
        
        Button button4 = new Button("X");
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                row_Delete(row_Id);
                }
            });
        
     hbox.getChildren().addAll(button1,button2,button3);
    
        return hbox;
    }
 
    public void quantity_Edit(int row, int q){
        
        Object[] args = new Object[2];
        args[1] = row;
        args[0] = q;
       
        db_Manager.commandManager("UPDATE_BOOK",args);
        submit_Button(1);
    }
    public void row_Delete(int row){
        
    }
    /* reset the table */
    public void table_Clear(){
        content.clear();
        /* TODO: flush the table displayed on fxml */
    }
  
}
