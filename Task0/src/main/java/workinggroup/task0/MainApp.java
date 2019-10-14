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
        final VBox container = new VBox();
        final HBox mainbox = new HBox();
        mainbox.getChildren().add(table);
        container.getChildren().add(mainbox);
        contr = new Task0Controller(container,mainbox,table);
    
       
        Scene scene = new Scene(new Group(), default_Width, default_Height);
        scene.getStylesheets().add("/styles/styles.css");

       
        Button button1 = new Button("Books");
        button1.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
           container.getChildren().clear();
           contr.submit_Button(1);
        }
});
       Button button2 = new Button("Authors");
      button2.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
       container.getChildren().clear();
       contr.submit_Button(2);
    }
});
       Button button3 = new Button("Publishers");
      button3.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
       container.getChildren().clear();
       contr.submit_Button(3);
    }
});
      
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(button1,button2,button3);
      
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hbox,mainbox);
        
        ((Group)scene.getRoot()).getChildren().addAll(vbox,container);
        
      
        stage.setTitle("Task0");
        stage.setScene(scene);
        stage.show();
    }
 
}
