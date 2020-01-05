package PopUpText;

import Utilidades.Alerts;
import Utilidades.ClientDir.Client;
import Utilidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.nio.ByteBuffer;

public class PopUpText_Controller {

    @FXML AnchorPane pn_text;
    @FXML TextField txt_dir;

    public void createDir(ActionEvent actionEvent){

        Usuario user = Usuario.getInstance();
        Client client = Client.getInstance();

        JSONObject enviar = new JSONObject();
        enviar.put("acc", "create");
        enviar.put("pwd", user.getPwd() + txt_dir.getText().trim() + "/");

        client.list.add(ByteBuffer.wrap(enviar.toString().getBytes()));
        System.out.println("Envio un mensaje.");

        Alerts alerta = new Alerts();
        Alert alert = alerta.loading();
        while (!client.isRespuesta()) {


            //Muestro la alerta
            alert.show();

        }
        alert.setResult(ButtonType.OK);


        JSONObject respuesta = client.getResponse();

        System.out.println(respuesta.toString(1));
        client.respuesta = false;

        if (respuesta.getString("R").equals("0")) {

            JSONObject infoAlert = new JSONObject();
            infoAlert.put("msg", respuesta.getString("M"));
            alerta.showAlert(infoAlert, "INFO");

            Stage stageP = (Stage) pn_text.getScene().getWindow();
            stageP.close();

        } else {
            JSONObject infoAlert = new JSONObject();
            infoAlert.put("msg", respuesta.getString("M"));
            alerta.showAlert(infoAlert, "ERROR");
        }


    }

}
