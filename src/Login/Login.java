package Login;

import Utilidades.ClientDir.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.sun.org.apache.xerces.internal.utils.SecuritySupport.getResourceAsStream;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Drive UCAB - "+this.getClass().getSimpleName());
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene login = new Scene(root, 400, 600);
        login.getStylesheets().add("styles/css/backgrounds.css");

        primaryStage.setScene(login);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

}
