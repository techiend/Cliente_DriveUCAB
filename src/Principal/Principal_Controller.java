package Principal;

import Utilidades.*;
import Utilidades.ClientDir.Client;
import Utilidades.Exceptions.SceneDontExist;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.ByteBuffer;

public class Principal_Controller {

    @FXML private Label file_name;
    @FXML private Label file_size;
    @FXML private Label file_lastmod;
    @FXML private AnchorPane menuPanel;
    @FXML private Pane manage_pane;
    @FXML private Pane pn_0;
    @FXML private Pane pn_1;
    @FXML private Pane pn_2;
    @FXML private Pane edit_pane;
    @FXML private GridPane mainPane;
    @FXML private Button btn_menu;
    @FXML private Button btn_opt2;
    @FXML private Button btn_opt3;
    @FXML private Label lb_option;
    @FXML private Label lb_pwd;

    private Constantes constantes = new Constantes();

    private FTPClient client = new FTPClient();
    private String ipServer = constantes.ip_server;
    private String user = constantes.user_ftp;
    private String password = constantes.pass_ftp;

    private String file_name_selected = "";
    private int openPanel = 0;
    private boolean menuIsOpen = false;

    public void initialize(){
        Usuario usuario = Usuario.getInstance();
        fillGridPane();
        edit_pane.setVisible(false);
        lb_pwd.setWrapText(true);
        lb_pwd.setText(usuario.getPwd());
        btn_opt2.setDisable(true);
        btn_opt3.setDisable(true);
    }

    // MANEJO DE MENU LATERAL

    public void animateMenuOpenClose(int value){
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        final KeyValue kv = new KeyValue(menuPanel.layoutXProperty(), value);
        final KeyValue kv_btn = new KeyValue(btn_menu.layoutXProperty(), (value == 0) ? 214 : 14);
        final KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
        final KeyFrame kf_btn = new KeyFrame(Duration.millis(200), kv_btn);
        timeline.getKeyFrames().add(kf);
        timeline.getKeyFrames().add(kf_btn);
        timeline.play();
    }

    public void menuOpenClose(ActionEvent actionEvent) {

        if (!menuIsOpen) {
//            menuPanel.setLayoutX(0);
            animateMenuOpenClose(0);
            menuIsOpen = true;
        }
        else{
//            menuPanel.setLayoutX(-200);
            animateMenuOpenClose(-200);
            menuIsOpen = false;
        }
    }

    public void optionLogout(ActionEvent actionEvent) {
        try {
            ScreenController screenController = new ScreenController();
            screenController.changeView(actionEvent, "Login");
        } catch (SceneDontExist sceneDontExist) {
            sceneDontExist.printStackTrace();
        }
    }

    public void openPanelHome(ActionEvent actionEvent) {

        if (openPanel == 1){
            pn_1.setLayoutY(612);
            pn_0.setLayoutY(58);
            openPanel = 0;
            lb_option.setText("Dashboard");

            animateMenuOpenClose(-200);
            menuIsOpen = false;
        }
        else if (openPanel == 2){
            pn_2.setLayoutY(612);
            pn_0.setLayoutY(58);
            openPanel = 0;
            lb_option.setText("Dashboard");

            animateMenuOpenClose(-200);
            menuIsOpen = false;
        }
    }

    public void openOption2(ActionEvent actionEvent) {
        if (openPanel == 2) {
            pn_2.setLayoutY(612);
        }
        else if (openPanel == 0){
            pn_0.setLayoutY(612);
        }

        pn_1.setLayoutY(58);
        openPanel = 1;
        lb_option.setText("Opcion #2");
    }

    public void openOption3(ActionEvent actionEvent) {

        if (openPanel == 0){
            pn_0.setLayoutY(612);
            pn_2.setLayoutY(58);
            openPanel = 2;
            lb_option.setText("Opción #3");

            animateMenuOpenClose(-200);
            menuIsOpen = false;
        }
        else if (openPanel == 1){
            pn_1.setLayoutY(612);
            pn_2.setLayoutY(58);
            openPanel = 2;
            lb_option.setText("Opción #3");

            animateMenuOpenClose(-200);
            menuIsOpen = false;
        }


    }

    // MANEJO DE ARCHIVOS VISUALMENTE

    public void addButton(JSONObject file, int fila, int col){
        final Button temp = new Button(file.getString("f_name"));
        temp.setPrefWidth(80);//100
        temp.setMaxWidth(80);
        temp.setPrefHeight(80);//84
        temp.setMaxHeight(80);
        if (file.getBoolean("f_dir")){

            if (!file.getString("f_name").equals("..")) {
                temp.setStyle("-fx-background-image: url('/styles/imgs/folder_32.png');" +
                        "-fx-background-position: center;" +
                        "-fx-background-repeat: no-repeat;");
            }
            else {
                temp.setStyle("-fx-background-image: url('/styles/imgs/undo_32.png');" +
                        "-fx-background-position: center;" +
                        "-fx-background-repeat: no-repeat;");
            }
        }
        else{

            temp.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (file_name_selected.equals("")) {
                        file_name.setText(file.getString("f_name"));
                        file_name.wrapTextProperty().setValue(true);

                        file_size.setText(file.getString("f_size"));
                        file_size.wrapTextProperty().setValue(true);

                        file_lastmod.setText(file.getString("f_lastMod"));
                        file_lastmod.wrapTextProperty().setValue(true);
                    }
                }
            });

            temp.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (file_name_selected.equals("")) {
                        file_name.setText("N/A");
                        file_name.wrapTextProperty().setValue(true);

                        file_size.setText("0 B");
                        file_size.wrapTextProperty().setValue(true);

                        file_lastmod.setText("N/A");
                        file_lastmod.wrapTextProperty().setValue(true);
                    }
                }
            });

            temp.setStyle("-fx-background-image: url('/styles/imgs/file_32.png');" +
                    "-fx-background-position: center;" +
                    "-fx-background-repeat: no-repeat;");
        }

        temp.getStyleClass().add("btnFiles");
        temp.setId(""+(fila*col));
//        temp.setWrapText(true);
        temp.textAlignmentProperty().set(TextAlignment.CENTER);
        temp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Tocaste el " + ((file.getBoolean("f_dir")) ? "directorio " : "archivo ") + file.getString("f_name"));

                if (!file.getBoolean("f_dir")) {

                    file_name.setText(file.getString("f_name"));
                    file_name.wrapTextProperty().setValue(true);

                    file_size.setText(file.getString("f_size"));
                    file_size.wrapTextProperty().setValue(true);

                    file_lastmod.setText(file.getString("f_lastMod"));
                    file_lastmod.wrapTextProperty().setValue(true);

                    edit_pane.setVisible(true);

                    file_name_selected = file.getString("f_name");
                }
                else{

                    // HACER LLAMADO A SERVIDOR, QUE TRAIGA DATA NUEVA

                    Usuario user = Usuario.getInstance();
                    Client client = Client.getInstance();

                    JSONObject enviar = new JSONObject();
                    enviar.put("acc", "list");
                    enviar.put("email", user.getEmail());

                    if (!file.getString("f_name").equals("..")) {
                        System.out.println(user.getPwd() + file.getString("f_name") + "/");
                        enviar.put("pwd", user.getPwd() + file.getString("f_name") + "/");
                    }
                    else {
                        String[] dirs = user.getPwd().split("/");
                        String replace = dirs[dirs.length-1] + "/";
                        System.out.println(user.getPwd().replace(replace,""));
                        enviar.put("pwd", user.getPwd().replace(replace,""));
                    }

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

                        user.setCurr_dir(file.getString("f_name")+"/");
                        user.setPwd(respuesta.getString("pwd"));
                        user.setFiles(respuesta.getJSONArray("files"));
                        lb_pwd.setText(respuesta.getString("pwd"));
                        clearGridPane();
                        fillGridPane();

                    } else {
                        JSONObject infoAlert = new JSONObject();
                        infoAlert.put("msg", respuesta.getString("M"));
                        alerta.showAlert(infoAlert, "ERROR");
                    }



                }

                System.out.println(file_name_selected);
            }
        });
        mainPane.add(temp, fila,col);
    }

    public void fillGridPane(){
        Usuario user = Usuario.getInstance();
        JSONArray files = user.getFiles();

        int fila = 0;
        int columna = 0;
        for (int f = 0; f < files.length(); f++) {
            if (columna == 6){
                fila++;
                columna = 0;
            }
            addButton(files.getJSONObject(f), columna , fila);
            columna++;
        }

        pn_0.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Pisaste raro");

                if (menuIsOpen){
                    animateMenuOpenClose(-200);
                    menuIsOpen = false;
                }

                edit_pane.setVisible(false);
                file_name_selected = "";

                file_name.setText("N/A");
                file_name.wrapTextProperty().setValue(true);

                file_size.setText("0 B");
                file_size.wrapTextProperty().setValue(true);

                file_lastmod.setText("N/A");
                file_lastmod.wrapTextProperty().setValue(true);


            }
        });

    }

    public void clearGridPane(){


        int fila = 0;
        int columna = 0;
        for (int f = 0; f < 6; f++) {
            if (columna == 6){
                fila++;
                columna = 0;
            }
            mainPane.getChildren().clear();
            columna++;
        }

    }

}
