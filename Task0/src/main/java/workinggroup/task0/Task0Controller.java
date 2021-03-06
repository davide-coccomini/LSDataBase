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
    @FXML private VBox edit_Container, insert_Container;
      
    /* initializations for the main table */
    public Task0Controller(VBox c, HBox mb, TableView<Object> t) {
        insert_Container = c;
        mainBox = mb;
        tableView = t;
        edit_Container = new VBox();
        edit_Container.setSpacing(5);
        edit_Container.setPadding(new Insets(30, 0, 0, 10));
        mainBox.getChildren().add(edit_Container);
        t.getStyleClass().add("TABLE");
        
        content = null;
        db_Manager = new DatabaseManager();
        
    }
    /* Event triggered by user click */
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
        else {
            System.out.println("Query result is empty");
        }
    }
    /* Creates table columns according to the results expected */
    private void format_Table(String query){
        
        buttons_Clear();
        form_Clear(); 
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
    /* Adds columns to main table to display a Book */
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
        date_Col = new TableColumn<>("Publication Year");
        date_Col.setCellValueFactory(new PropertyValueFactory("year"));
        TableColumn<Object,String> cat_Col;
        cat_Col = new TableColumn<>("Book Category");
        cat_Col.setCellValueFactory(new PropertyValueFactory("category"));
        TableColumn<Object,Integer> q_Col;
        q_Col = new TableColumn<>("Copies In Stock");
        q_Col.setCellValueFactory(new PropertyValueFactory("quantity"));             
  
        tableView.getColumns().setAll(id_Col,title_Col, price_Col, pages_Col, date_Col, cat_Col, q_Col);
       
        generate_Edit_Buttons();
        generate_Form_Book();
    }
    /* Adds columns to main table to display an author */
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
     /* Adds columns to main table to display a publisher */
    private void format_Publisher(){   
        TableColumn<Object,String> pub_Col;
        pub_Col = new TableColumn<>("Publisher Name");
        pub_Col.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Object,String> loc_Col;
        loc_Col = new TableColumn<>("Publisher Location");
        loc_Col.setCellValueFactory(new PropertyValueFactory("location"));
        
        tableView.getColumns().setAll(pub_Col,loc_Col);
    }
    /* Makes the whole column of edit buttons */
    private void generate_Edit_Buttons(){
         
        Book b;
        for(Object line:content) {
               
            b =(Book)line;
            
            HBox hbox = make_Edit_Buttons(b.getIdBOOK(), b.getQuantity());
            hbox.getStyleClass().add("Edit_Container");
            edit_Container.getChildren().add(hbox);
           
        }
        
    }
    /* Lets the user add a new book */
    private void generate_Form_Book(){
        VBox vbox = new VBox();
        final TextField title = new TextField();
        title.setPromptText("Title");
        final TextField numPages = new TextField();
        numPages.setPromptText("Pages");
        final TextField quantity = new TextField();
        quantity.setPromptText("Quantity");
        final TextField category = new TextField();
        category.setPromptText("Category");
        final TextField price = new TextField();
        price.setPromptText("Price");
        final TextField publicationYear = new TextField();
        publicationYear.setPromptText("Publication Year (YYYY)");
        final TextField author = new TextField();
        author.setPromptText("Author ID");
        final TextField publisher = new TextField();
        publisher.setPromptText("Publisher ID");
          
        Button button = new Button("Add Book");
        button.setOnAction(new EventHandler<ActionEvent>() {
         @Override public void handle(ActionEvent e) {
                insert_Book(title.getText(), numPages.getText(), quantity.getText(), category.getText(), price.getText(), publicationYear.getText(), author.getText(), publisher.getText());
          }
        });
            
        vbox.getChildren().addAll(title,numPages,quantity,category,price,publicationYear,author,publisher,button);
        insert_Container.getStyleClass().add("Insert_Container");
        
        insert_Container.getChildren().add(vbox);
    }
    /* Prepares the buttons to edit books::quantity in each row */
    private HBox make_Edit_Buttons(final int row_Id,final int quantity){
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
      //  hbox.SimpleStringProperty("row");
        
      
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
        
       
        
        EditButton button1 = new EditButton("+",row_Id,quantity+1,this);
        EditButton button3 = new EditButton("-",row_Id,(quantity-1)<0?0:quantity-1,this);
        EditButton button4 = new EditButton("X",row_Id,0,this);
 
        button2.getStyleClass().add("SQUARE_EDIT");
  
        hbox.getChildren().addAll(button1,button2,button3,button4);
    
        return hbox;
    }
 
    public void quantity_Edit(int row, int q){
        Object[] args = new Object[2];
        args[1] = row;
        args[0] = q;

        if(q >= 0){
            db_Manager.commandManager("UPDATE_BOOK",args);
            submit_Button(1);
        }
    }
    public void insert_Book(String title,String numPages,String quantity,String category,String price,String publicationDate, String author,String publisher){
        Object[] args = new Object[8];
        args[0] = title;
        args[1] = numPages;
        args[2] = quantity;
        args[3] = category;
        args[4] = price;
        args[5] = publicationDate;
        args[6] = author;
        args[7] = publisher;
        db_Manager.commandManager("INSERT_BOOK",args);
        submit_Button(1);
    }
    
    public void row_Delete(int row){
        Object[] args = new Object[1];
        args[0] = row;
        content = db_Manager.commandManager("DELETE_BOOK", args);
        submit_Button(1);
    }
    /* Resets the table */
    public void buttons_Clear(){
        edit_Container.getChildren().clear();
    }
  
    public void form_Clear(){
        insert_Container.getChildren().clear();
    }
}
