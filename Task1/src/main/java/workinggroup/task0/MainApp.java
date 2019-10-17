package workinggroup.task0;

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

    private TableView table = new TableView();
    private Task0Controller contr;
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        table.setPrefSize( 800, 700 );
        final VBox container = new VBox();
        container.setPadding(new Insets(100, 0, 0, 50));
        final HBox mainbox = new HBox();
        mainbox.getChildren().add(table);
        container.getChildren().add(mainbox);
        contr = new Task0Controller(container,mainbox,table);
   
        Scene scene = new Scene(new Group(), default_Width, default_Height);
        scene.getStylesheets().add("/styles/styles.css");

        Button button1 = new Button("Books");       //SELECT * FROM BOOK
        button1.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
           container.getChildren().clear();
           contr.submit_Button(1);
        }
});
       Button button2 = new Button("Authors");      //SELECT * FORM AUTHOR
      button2.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
       container.getChildren().clear();
       contr.submit_Button(2);
    }
});
       Button button3 = new Button("Publishers");   //SELECT * FROM PUBLISHER
      button3.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
       container.getChildren().clear();
       contr.submit_Button(3);
    }
});
      
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
        
      
        stage.setTitle("Task0");
        stage.setScene(scene);
        stage.show();
    }
 
}
