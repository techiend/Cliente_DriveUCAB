package Utilidades;

import Utilidades.Exceptions.SceneDontExist;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenController {

    public void changeView(ActionEvent actionEvent, String scene) throws SceneDontExist {
        try {
            //Obtenemos el Stage
            Stage ventana = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            //Manejamos las Scenes
            Parent parent = null;
            Scene scena = null;

            switch (scene){
                case "Login":
                    parent = FXMLLoader.load(getClass().getClassLoader().getResource("Login/login.fxml"));
                    scena = new Scene(parent, 400, 600);
                    scena.getStylesheets().add("styles/css/backgrounds.css");
                    ventana.setTitle("Drive UCAB - " + scene);
                    break;
                case "Principal":
                    parent = FXMLLoader.load(getClass().getClassLoader().getResource("Principal/principal.fxml"));
                    scena = new Scene(parent, 900, 600);
                    ventana.setTitle("Drive UCAB - " + scene);
                    scena.getStylesheets().add("styles/css/backgrounds.css");
                    break;
                case "Register":
                    System.out.println("Entreeeee....");
                    parent = FXMLLoader.load(getClass().getClassLoader().getResource("Registro/registro.fxml"));
                    scena = new Scene(parent, 400, 600);
                    ventana.setTitle("Drive UCAB - " + scene);
                    scena.getStylesheets().add("styles/css/backgrounds.css");
                    break;
                default:
                    throw new SceneDontExist();
            }


            ventana.setScene(scena);
            ventana.show();
        }
        catch (IOException e){
            e.printStackTrace();
            throw new SceneDontExist();
        }
    }
}