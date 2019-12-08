package Utilidades;

import Utilidades.ClientDir.Client;

public class Constantes {

    public String ip_server = "24.63.57.111";
    public int port_server = 23315;
    public String user_ftp = "cverde";
    public String pass_ftp = "24635783";
    public String dir_base = "/home/cverde/REDES/USERS/";

    private Constantes(){}

    public static Constantes getInstance() {
        return Constantes.NewSingletonHolder.INSTANCE;
    }

    private static class NewSingletonHolder {
        private static final Constantes INSTANCE = new Constantes();
    }

}
