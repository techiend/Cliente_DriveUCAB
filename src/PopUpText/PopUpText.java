package PopUpText;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PopUpText extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Drive UCAB - "+this.getClass().getSimpleName());
        Parent root = FXMLLoader.load(getClass().getResource("popuptext.fxml"));

        Scene popup = new Scene(root, 400, 150);
        popup.getStylesheets().add("styles/css/backgrounds.css");

        primaryStage.setScene(popup);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


}
