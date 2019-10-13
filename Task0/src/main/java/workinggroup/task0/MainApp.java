package workinggroup.task0;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    public final int default_Width = 600;
    public final int default_Height = 400;


    public static void main(String[] args) throws Exception {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {


        String fxmlFile = "/fxml/index.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));


        Scene scene = new Scene(rootNode, default_Width, default_Height);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Task0");
        stage.setScene(scene);
        stage.show();
    }
}
