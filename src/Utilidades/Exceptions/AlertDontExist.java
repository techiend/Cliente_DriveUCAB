package Utilidades.Exceptions;

public class AlertDontExist extends Exception {

    @Override
    public String toString() {
        return "La alerta que ha solicitado no se encuentra especificada.";
    }
}
