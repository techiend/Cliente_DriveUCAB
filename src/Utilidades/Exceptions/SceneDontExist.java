package Utilidades.Exceptions;

public class SceneDontExist extends Exception {

    @Override
    public String toString() {
        return "La escena que ha solicitado no se encuentra especificada.";
    }
}
