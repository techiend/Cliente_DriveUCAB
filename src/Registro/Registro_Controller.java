package Registro;

import Utilidades.Alerts;
import Utilidades.ClientDir.Client;
import Utilidades.Constantes;
import Utilidades.Exceptions.SceneDontExist;
import Utilidades.ScreenController;
import Utilidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.nio.ByteBuffer;

public class Registro_Controller {


    @FXML public Label lb_error_r;
    @FXML public TextField txt_name;
    @FXML public TextField txt_lname;
    @FXML public TextField txt_user;
    @FXML public PasswordField txt_pass;
    @FXML public PasswordField txt_pass_conf;

    private Constantes constantes = Constantes.getInstance();

    public void initialize(){

    }

    public void validateRegister(ActionEvent actionEvent) {
        try {
            if (
                    !txt_name.getText().trim().equals("") &&
                            !txt_lname.getText().trim().equals("") &&
                            !txt_user.getText().trim().equals("") &&
                            !txt_pass.getText().trim().equals("") &&
                            !txt_pass_conf.getText().trim().equals("")
            ) {
                if (txt_pass.getText().trim().equals(txt_pass_conf.getText().trim())) {


                    Client client = Client.getInstance();

                    JSONObject enviar = new JSONObject();
                    enviar.put("acc", "register");
                    enviar.put("name", txt_name.getText().trim());
                    enviar.put("lname", txt_lname.getText().trim());
                    enviar.put("p", txt_pass.getText().trim());
                    enviar.put("email", txt_user.getText().trim());


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

                        ScreenController screenController = new ScreenController();
                        screenController.changeView(actionEvent, "Login");
                    } else {
                        lb_error_r.setText(respuesta.getString("M"));
                    }

                } else {
                    lb_error_r.setText("Las claves deben ser iguales.");
                }
            } else {
                lb_error_r.setText("Todos los campos son obligatorios");
            }
        } catch (SceneDontExist sceneDontExist) {
            sceneDontExist.printStackTrace();
        }
    }
}
