package workinggroup.task1;

import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainApp extends Application {

    public final int default_Width = 1200;
    public final int default_Height = 800;

    private final TableView table = new TableView();
    private UIController contr;
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final VBox container = new VBox();
        container.setPadding(new Insets(100, 0, 0, 50));
        final HBox mainbox = new HBox();
        mainbox.getChildren().add(table);
        container.getChildren().add(mainbox);
        contr = new UIController(container,mainbox,table);
   
        Scene scene = new Scene(new Group(), default_Width, default_Height);
        scene.getStylesheets().add("/styles/styles.css");

        final Button button1 = new Button("Books");        //BROWSE BOOK
        final Button button2 = new Button("Authors");      //BROWSE AUTHOR
        final Button button3 = new Button("Publishers");   //BROWSE PUBLISHER
        
        button1.getStyleClass().add("button");
        button2.getStyleClass().add("button");
        button3.getStyleClass().add("button");
       
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               container.getChildren().clear();
               contr.submit_Button(1);
               set_Button_Style(1,button1,button2,button3);
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                container.getChildren().clear();
                contr.submit_Button(2);
                set_Button_Style(2,button1,button2,button3);
            }
        });
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
             container.getChildren().clear();
             contr.submit_Button(3);
             set_Button_Style(3,button1,button2,button3);
            }
        });

        contr.submit_Button(1);
        set_Button_Style(1,button1,button2,button3);
        final HBox hbox = new HBox(); // 3 Buttons container
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(button1,button2,button3);
      
        final VBox vbox = new VBox(); // General Container
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hbox,mainbox);
        
        final HBox body = new HBox();
        body.setSpacing(5);
        body.setPadding(new Insets(10, 0, 0, 10));
        body.getChildren().addAll(vbox,container);
        
        ((Group)scene.getRoot()).getChildren().addAll(body);
        
      
        stage.setTitle("Task1");
        stage.setScene(scene);
        stage.show();
    }
    private void set_Button_Style(int section, Button b1, Button b2, Button b3){
       Button mainButton = (section==1)?b1:(section==2)?b2:b3;
       
       b1.getStyleClass().removeAll("buttonFocus"); 
       b2.getStyleClass().removeAll("buttonFocus"); 
       b3.getStyleClass().removeAll("buttonFocus"); 
       
       b1.getStyleClass().add("button");
       b2.getStyleClass().add("button");
       b3.getStyleClass().add("button");
       
       mainButton.getStyleClass().add("buttonFocus");
       
    }
}
