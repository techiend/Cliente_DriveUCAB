package Config;


import Utilidades.Alerts;
import Utilidades.Constantes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

public class Config_Controller {

    @FXML
    AnchorPane pn_text;
    @FXML
    TextField txt_ip;
    @FXML
    TextField txt_port;
    @FXML
    Label lb_error;

    public void setConfig(ActionEvent actionEvent) {

        Constantes constantes = Constantes.getInstance();

        if (!txt_ip.getText().equals("")){
            if (!txt_port.getText().equals("")){
                constantes.ip_server = txt_ip.getText();
                constantes.port_server = Integer.parseInt(txt_port.getText());

                Alerts alerta = new Alerts();
                JSONObject infoAlert = new JSONObject();
                infoAlert.put("msg", "Se ha seteado correctamente la IP y el Puerto del servidor.");
                alerta.showAlert(infoAlert, "INFO");

                Stage stageP = (Stage) pn_text.getScene().getWindow();
                stageP.close();
            }
            else {
                lb_error.setText("El Puerto no puede estar vacío.");
            }
        }
        else{
            lb_error.setText("La IP no puede estar vacía.");
        }

    }
}
