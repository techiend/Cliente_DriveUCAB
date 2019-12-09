package Login;

import Utilidades.Alerts;
import Utilidades.ClientDir.Client;
import Utilidades.Exceptions.SceneDontExist;
import Utilidades.ScreenController;
import Utilidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.nio.ByteBuffer;

public class Login_Controller {

    @FXML private Button btn_login;
    @FXML private Label lb_error;
    @FXML private TextField txt_user;
    @FXML private PasswordField txt_pass;

    public void validateData(ActionEvent actionEvent) {

        try {

            System.out.println("U: " + txt_user.getText());
            System.out.println("P: " + txt_pass.getText());

            if (!txt_user.getText().trim().equals("") && !txt_pass.getText().trim().equals("")) {

                Client client = Client.getInstance();

                JSONObject enviar = new JSONObject();
                enviar.put("acc", "login");
                enviar.put("email", txt_user.getText().trim());
                enviar.put("p", txt_pass.getText().trim());

                client.list.add(ByteBuffer.wrap(enviar.toString().getBytes()));
                System.out.println("Envio un mensaje.");

                Alerts alerta = new Alerts();
                Alert alert = alerta.loading();
                while (!client.isRespuesta()){


                    //Muestro la alerta
                    alert.show();

                }
                alert.setResult(ButtonType.OK);


                JSONObject respuesta = client.getResponse();

                System.out.println(respuesta.toString(1));
                client.respuesta = false;

                if (respuesta.getString("R").equals("0")){

                    Usuario user = Usuario.getInstance();

                    user.setId(respuesta.getInt("u_id"));
                    user.setName(respuesta.getString("u_name"));
                    user.setLname(respuesta.getString("u_lname"));
                    user.setPwd(respuesta.getString("u_pwd"));
                    user.setEmail(txt_user.getText().trim());
                    user.setFiles(respuesta.getJSONArray("files"));
                    user.setMaxSpace(respuesta.getString("u_max"));
                    user.setMaxSpaceNum(respuesta.getLong("u_max_num"));

                    ScreenController screenController = new ScreenController();
                    screenController.changeView(actionEvent, "Principal");
                }
                else{
                    lb_error.setText(respuesta.getString("M"));
                }


            } else {
                lb_error.setText("Usuario/Contraseña no pueden ser vacios.");
            }
        } catch (SceneDontExist e) {
            e.printStackTrace();
        }
    }
}
