package workinggroup.task1;
 
import java.sql.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import workinggroup.task1.Obj.Author;
import workinggroup.task1.Obj.Book;
import workinggroup.task1.Obj.Publisher;

public class UIController
{
    private ObservableList<Object> content;
    private int busy = 0;
    
    @FXML private TableView<Object> tableView;
    @FXML private HBox mainBox;
    @FXML private VBox edit_Container, insert_Container;
      
    /* initializations for the main table */
    public UIController(VBox c, HBox mb, TableView<Object> t) {
        insert_Container = c;
        mainBox = mb;
        tableView = t;
       
        edit_Container = new VBox();
        edit_Container.setSpacing(5);
        edit_Container.setPadding(new Insets(30, 0, 0, 10));
        mainBox.getChildren().add(edit_Container);
        t.getStyleClass().add("TABLE");
        
        content = null;
        
    }
    /* Event triggered by user click */
    @FXML public void submit_Button(int button_Code){
     
        if(buttons_Are_Locked()) 
        {
            // if any operation is in progress, the current request is delayed
            mutex_Queue(button_Code);
            return;
        }
        
        buttons_Lock();
        
        String query;          
         
        switch(button_Code){
            default:
            case 1:
                query = "SELECT_ALL_BOOKS";
                BookManager bmanager = new BookManager();
                bmanager.setup();
                content = bmanager.selectAllBooks();
                break;
            case 2:
                query = "SELECT_ALL_AUTHORS";
                AuthorManager amanager = new AuthorManager();
                amanager.setup();
                content = amanager.selectAllAuthors();
                break;
            case 3:
                query = "SELECT_ALL_PUBLISHERS";
                PublisherManager pmanager = new PublisherManager();
                pmanager.setup();
                content = pmanager.selectAllPublishers();
                break;
        }
     
        if((content!=null)&&(content.isEmpty()==false)){
            tableView.setItems(content);
            format_Table(query);
        }
        else {
            System.out.println("Query result is empty");
        }
        
        buttons_Unlock();
        
    }
    
    /* check the mutex status */
    private boolean buttons_Are_Locked(){
        /* return false if server is free and user can operate */
        /* return true if server is already busy, the request is stored */
        if(busy==0) 
            return false;
        else
            return true;
    }
    /* lock the mutex for user operations */
    private void buttons_Lock(){
        
        if(busy==0)
            busy = -1;
    }
    /* push a request in the mutex queue */
    private void mutex_Queue(int queue){   
        /* the operation is stored in queue */
        /* queue can store only one operation (the last request asked by user)*/
        if(queue<0)
            busy = queue;
    }
    private void buttons_Unlock(){
        /* unlock the mutex for user operations */
        /* call the operation that was in queue, if there was one */
        int queue = busy;
        busy = 0;
        if(queue>0)
        {
            submit_Button(queue);
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
        date_Col.setCellValueFactory(new PropertyValueFactory("publicationYear"));
        TableColumn<Object,String> cat_Col;
        cat_Col = new TableColumn<>("Book Category");
        cat_Col.setCellValueFactory(new PropertyValueFactory("category"));
        TableColumn<Object,Integer> q_Col;
        q_Col = new TableColumn<>("Copies In Stock");
        q_Col.setCellValueFactory(new PropertyValueFactory("quantity"));             
  
        
        TableColumn authors_Col = new TableColumn("Authors");
  
        authors_Col.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Object, String>, TableCell<Object, String>> authorsCellFactory
                = //
                new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
            @Override
            public TableCell call(final TableColumn<Object, String> param) {
                final TableCell<Object, String> cell = new TableCell<Object, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(getIndex()<content.size() && getIndex() >= 0){
                            if(content.get(getIndex()).getClass()==Book.class) {
                                String authorsString = "";
                                Book b = (Book)content.get(getIndex());
                                List<Author> authors = b.getAuthors();
                                for(int i=0; i<authors.size();i++){
                                    String firstName = authors.get(i).getFirstName();
                                    String lastName = authors.get(i).getLastName();
                                    String authorName = firstName + " " + lastName;
                                    if(i==0)
                                        authorsString += authorName;
                                    else
                                        authorsString += ", " + authorName;
                                }
                                setText(authorsString);
                            }
                        }       
                    }
                };
                return cell;
            }
        };
        authors_Col.setCellFactory(authorsCellFactory);
        
        
        
        TableColumn publisher_Col = new TableColumn("Publisher");
  
        publisher_Col.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Object, String>, TableCell<Object, String>> publisherCellFactory
                = //
                new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
            @Override
            public TableCell call(final TableColumn<Object, String> param) {
                final TableCell<Object, String> cell = new TableCell<Object, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(getIndex()<content.size() && getIndex() >= 0){
                            if(content.get(getIndex()).getClass()==Book.class) {
                                String authorsString = "";
                                Book b = (Book)content.get(getIndex());
                                Publisher p = b.getPublisher();
                                setText(p.getName());
                            }
                        }       
                    }
                };
                return cell;
            }
        };
        publisher_Col.setCellFactory(publisherCellFactory);
        
        
        q_Col.setCellValueFactory(new PropertyValueFactory("quantity"));    
        
        TableColumn action_Col = new TableColumn("Action");
        action_Col.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Object, String>, TableCell<Object, String>> cellFactory
                = //
                new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
            @Override
            public TableCell call(final TableColumn<Object, String> param) {
                final TableCell<Object, String> cell = new TableCell<Object, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(getIndex()<content.size() && getIndex() >= 0){
                            if(content.get(getIndex()).getClass()==Book.class) {
                            
                                Book b = (Book)content.get(getIndex());
                                HBox hbox = make_Edit_Buttons(b.getIdBOOK(),b.getQuantity());
                                setGraphic(hbox);
                                setText(null);
                            }
                        }       
                    }
                };
                return cell;
            }
        };
        action_Col.setCellFactory(cellFactory);
        
        tableView.getColumns().setAll(action_Col, id_Col,title_Col, price_Col, pages_Col, date_Col, cat_Col, q_Col, authors_Col, publisher_Col);
        
        generate_Form_Book();
    }
    /* Adds columns to main table to display an author */
    private void format_Author(){   
        TableColumn<Object,Integer> id_Col;
        id_Col = new TableColumn<>("Author Id");
        id_Col.setCellValueFactory(new PropertyValueFactory("idAUTHOR"));
        TableColumn<Object,String> fn_Col;
        fn_Col = new TableColumn<>("Author Name");
        fn_Col.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Object,String> ln_Col;
        ln_Col = new TableColumn<>("Author Surname");
        ln_Col.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Object,String> bio_Col;
        bio_Col = new TableColumn<>("Author's Bio");
        bio_Col.setCellValueFactory(new PropertyValueFactory("biography"));
  
        TableColumn action_Col = new TableColumn("Action");
        action_Col.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Object, String>, TableCell<Object, String>> cellFactory
                = //
                new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
            @Override
            public TableCell call(final TableColumn<Object, String> param) {
                final TableCell<Object, String> cell = new TableCell<Object, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(getIndex()<content.size() && getIndex() >= 0){
                            if(content.get(getIndex()).getClass()==Author.class) {
                                
                                Author a = (Author)content.get(getIndex());
                                Button delBtn = make_Delete_Button(a.getIdAUTHOR(), 2);
                                setGraphic(delBtn);
                                setText(null);
                                
                            }
                        }       
                    }
                };
                return cell;
            }
        };
        action_Col.setCellFactory(cellFactory);
        
        
        tableView.getColumns().setAll(action_Col, id_Col,fn_Col,ln_Col,bio_Col);
        generate_Form_Author();
    }
    
     /* Adds columns to main table to display a publisher */
    private void format_Publisher(){   
        TableColumn<Object,Integer> id_Col;
        id_Col = new TableColumn<>("Publisher Id");
        id_Col.setCellValueFactory(new PropertyValueFactory("idPUBLISHER"));
        TableColumn<Object,String> pub_Col;
        pub_Col = new TableColumn<>("Publisher Name");
        pub_Col.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Object,String> loc_Col;
        loc_Col = new TableColumn<>("Publisher Location");
        loc_Col.setCellValueFactory(new PropertyValueFactory("location"));
        
        TableColumn action_Col = new TableColumn("Action");
        action_Col.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Object, String>, TableCell<Object, String>> cellFactory
                = //
                new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
            @Override
            public TableCell call(final TableColumn<Object, String> param) {
                final TableCell<Object, String> cell = new TableCell<Object, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(getIndex()<content.size() && getIndex() >= 0){
                            if(content.get(getIndex()).getClass()==Publisher.class) {
                                
                                Publisher p = (Publisher)content.get(getIndex());
                                Button delBtn = make_Delete_Button(p.getIdPUBLISHER(), 3);
                                setGraphic(delBtn);
                                setText(null);
                                
                            }
                        }       
                    }
                };
                return cell;
            }
        };
        action_Col.setCellFactory(cellFactory);
        
        tableView.getColumns().setAll(action_Col, id_Col,pub_Col,loc_Col);
        generate_Form_Publisher();
    }
    
    /* Lets the user add a new book */
    private void generate_Form_Book(){
        final VBox vbox = new VBox();
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
        
        final VBox columnAuthor = new VBox();
        Button addAuthor = new Button("+");
        addAuthor.setOnAction(new EventHandler<ActionEvent>() {
         @Override public void handle(ActionEvent e) {
              final TextField newAuthor = new TextField();
              newAuthor.setPromptText("Author ID");
              columnAuthor.getChildren().add(newAuthor);
          }
        });

        final HBox rowAuthor = new HBox();
        rowAuthor.getChildren().addAll(author,addAuthor);
        columnAuthor.getChildren().add(rowAuthor);
        Button button = new Button("Add Book");
        button.setOnAction(new EventHandler<ActionEvent>() {
         @Override public void handle(ActionEvent e) {
                String authors[] = new String[columnAuthor.getChildren().size()];
                authors[0] = author.getText();
                int i=0;
                for(Node author:columnAuthor.getChildren()){
                    if(i==0){ // Skip the author with the button
                        i++;
                        continue;
                    }
                    authors[i] = ((TextField) author).getText();
                    i++;
                }
                insert_Book(title.getText(), numPages.getText(), quantity.getText(), category.getText(), price.getText(), publicationYear.getText(), authors, publisher.getText());
          }
        });
        
        vbox.getChildren().addAll(title,numPages,quantity,category,price,publicationYear,columnAuthor,publisher,button);
        insert_Container.getStyleClass().add("Insert_Container");
        insert_Container.getChildren().add(vbox);
    }
    
    /* Lets the user add a new author */
    private void generate_Form_Author(){
        final VBox vbox = new VBox();
        final TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        final TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        final TextField biography = new TextField();
        biography.setPromptText("Biography");
        
        final Button button = new Button("Add Author");
        
        button.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            
                Author newAuthor = new Author();
                insert_Author(firstName.getText(), lastName.getText(), biography.getText());
                
          }

        });

        vbox.getChildren().addAll(firstName, lastName, biography,button);
        insert_Container.getStyleClass().add("Insert_Container");
        insert_Container.getChildren().add(vbox);
        
    }
    
    /* Lets the user add a new author */
    private void generate_Form_Publisher(){
        final VBox vbox = new VBox();
        final TextField name = new TextField();
        name.setPromptText("Name");
        final TextField location = new TextField();
        location.setPromptText("Location");
        
        final Button button = new Button("Add Publisher");
        
        button.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            
                Publisher newPublisher = new Publisher();
                insert_Publisher(name.getText(), location.getText());
                
          }

        });

        vbox.getChildren().addAll(name, location,button);
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
                        String t = button2.getText();
                        if("".equals(t))
                            return;
                        int val = Integer.parseInt(t);
                        quantity_Edit(row_Id,val);
                    }
                }
            });
        
        
        
        EditButton button1 = new EditButton("+",row_Id,quantity+1,this,1);
        EditButton button3 = new EditButton("-",row_Id,(quantity-1)<0?0:quantity-1,this,1);
        EditButton button4 = new EditButton("X",row_Id,0,this,1);
 
        button2.getStyleClass().add("SQUARE_EDIT");
  
        hbox.getChildren().addAll(button1,button2,button3,button4);
    
        return hbox;
    }
 
    /**/
    private Button make_Delete_Button(final int row_Id, final int type){
        final Button delButton = new EditButton("X",row_Id, 0, this, type);
        return delButton;
    }
    
    public void quantity_Edit(int row, int q){
        if(q >= 0){
             
            BookManager bmanager = new BookManager();
            bmanager.setup();
            bmanager.update(row, q);
            
            //refresh page content
            submit_Button(1);
        }
    }
    public void insert_Book(String title,String numPages,String quantity,String category,String price,String publicationDate, String[] authors,String publisher){
   
        //se mancano degli argomenti, ci pensa l'sql a invalidare l'operazione
        BookManager bmanager = new BookManager();
        bmanager.setup();
        bmanager.create(title,numPages,quantity,category,price,publicationDate,authors,publisher);
        
        //refresh page content
        submit_Button(1);
    }
    
    public void insert_Author(String firstName, String lastName, String biography) {
        AuthorManager aManager = new AuthorManager();
        aManager.setup();
        aManager.create(firstName, lastName, biography, null);
        
        //refresh page content
        submit_Button(2);
    }
    
    public void insert_Publisher(String name, String location) {
        PublisherManager pManager = new PublisherManager();
        pManager.setup();
        pManager.create(name, location, null);
        
        //refresh page content
        submit_Button(3);
    }
    
    /* row = object id; type = type of object whom */
    public void row_Delete(int row, int type){
        if(row <=0) 
            return;
        /*
        type : 
        1 = BOOK
        2 = AUTHOR
        3 = PUBLISHER
        */
        switch(type){
            case 1 : 
                BookManager bmanager = new BookManager();
                bmanager.setup();
                bmanager.delete(row);

                break;
            case 2 : 
                AuthorManager amanager = new AuthorManager();
                amanager.setup();
                amanager.delete(row);

                break;
            case 3 : 
                PublisherManager pmanager = new PublisherManager();
                pmanager.setup();
                pmanager.delete(row);

                break;        
        }
        
        submit_Button(type);
        
    }
    /* Resets the table */
    public void buttons_Clear(){
        edit_Container.getChildren().clear();
    }
  
    public void form_Clear(){
        insert_Container.getChildren().clear();
    }
}
