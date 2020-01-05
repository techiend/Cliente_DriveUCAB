package Registro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Registro extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("registro.fxml"));
        primaryStage.setTitle("Drive UCAB - "+this.getClass().getSimpleName());

        Scene registro = new Scene(root, 400, 600);
        registro.getStylesheets().add("styles/css/backgrounds.css");

        primaryStage.setScene(registro);
        primaryStage.setResizable(false);
        primaryStage.show();
    }



}
