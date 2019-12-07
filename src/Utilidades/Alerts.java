package Utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.JSONObject;

import java.util.Optional;

public class Alerts {

    /**
     *
     * data:
     *      (Opc)   header - Cabecera de la alerta
     *              msg - Mensaje de la alerta
     * type:
     *              ERROR
     *              WARN
     *              INFO
     *              CONF (Confirmacion)
     *
     * return:
     *
     *              true - Solamente si es type = CONF y el usuario le dio clic a OK
     *              false - si el type != CONF o si type = CONF y le dio clic a Cancelar
     *
     *
     * */
    public boolean showAlert(JSONObject data, String type) {

        Alert alert = null;
        switch (type) {
            case "ERROR":
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ha ocurrido un error");
                break;
            case "WARN":
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ey, cuidado con esto");
                break;
            case "INFO":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ten en cuenta que....");
                break;
            case "CONF":
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Me podrías decir si...");
                break;
        }


//        if (data.has("header")) {
//            alert.setHeaderText(data.getString("header"));
//        }

        alert.setHeaderText(data.getString("msg"));


        if (!type.equals("CONF")) {
            alert.showAndWait();
            return false;
        }
        else {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                return true;
            } else {
                // ... user chose CANCEL or closed the dialog
                return false;
            }
        }

    }

    public Alert loading(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cargando...");
        alert.setHeaderText("Conectando con el servidor...");
        alert.getButtonTypes().clear();

        return alert;
    }

}
