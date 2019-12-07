package Principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Principal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("principal.fxml"));
        primaryStage.setTitle("Drive UCAB - "+this.getClass().getSimpleName());

        Scene principal = new Scene(root, 900, 600);
        principal.getStylesheets().add("styles/css/backgrounds.css");

        primaryStage.setScene(principal);
        primaryStage.setResizable(false);
        primaryStage.show();
    }



}
