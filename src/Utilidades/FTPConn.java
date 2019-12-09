package Utilidades;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONObject;

import java.io.*;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class FTPConn {

    Constantes constantes;
    FTPClient client;

    private FTPConn() {

        constantes = Constantes.getInstance();
        client = new FTPClient();


        try
        {
            client.setConnectTimeout(1000000);

                client.connect(constantes.ip_server, 21);
            boolean login = client.login(constantes.user_ftp,constantes.pass_ftp);
            System.out.println(client.getReplyString());
            if (login)
            {

                System.out.println("FTP Login con exito.");
            }
            else {
                System.out.println("FTP login Failed...");

            }


//            client.logout();
//            client.disconnect();
//            System.out.println("0Disconnected");
        }
        catch (SocketException e){
            e.printStackTrace();
            System.out.println("IP inalcanzable. Por favor, verificar la IP del servidor.");

        }
        catch (SocketTimeoutException e){
            e.printStackTrace();
            System.out.println("Conexión TimeOut...");

        }
        catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("Archivo no existe.");

        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Intento fallido al conectar con el servidor, trata nuevamente.");

        }

    }

    public static FTPConn getInstance(){
        return NewSingletonHolder.INSTANCE;
    }

    private static class NewSingletonHolder{
        private static final FTPConn INSTANCE = new FTPConn();
    }

    public JSONObject uploadFTP(String remoteFileName, String localFileName){
        JSONObject response = new JSONObject();

        try
        {

                String[] dataLocal = localFileName.split("\\\\");

                String remote = constantes.dir_base +remoteFileName+dataLocal[dataLocal.length-1];
                System.out.println("remote: "+remote);
                System.out.println("local: " +localFileName);

                client.enterLocalPassiveMode();


                File firstLocalFile = new File(localFileName);

                FileInputStream  inputLocal = new FileInputStream(firstLocalFile);

                boolean done = client.storeFile(remote, inputLocal);
                System.out.println(client.getReplyString());
                inputLocal.close();

                if (done) {
                    response.put("R", "0");
                    response.put("M", "Archivo actualizado correctamente.");

                }
                else {
                    response.put("R", "1");
                    response.put("M", "Ocurrio un error al actualizar el archivo.");
                }


                return response;

        }
        catch (SocketException e){
            e.printStackTrace();
            System.out.println("IP inalcanzable. Por favor, verificar la IP del servidor.");

            response.put("M", "IP inalcanzable. Por favor, verificar la IP del servidor.");
        }
        catch (SocketTimeoutException e){
            e.printStackTrace();
            System.out.println("Conexión TimeOut...");

            response.put("M", "Conexión TimeOut...");
        }
        catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("Archivo no existe.");

            response.put("M", "Archivo no existe.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Intento fallido al conectar con el servidor, trata nuevamente.");

            response.put("M", "Intento fallido al conectar con el servidor, trata nuevamente.");
        }

        response.put("R", "1");

        return response;
    }

}
