package Utilidades;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class FTPConn {

    Constantes constantes = new Constantes();

    public JSONObject getListProperties (FTPClient client, String ipServer, String user, String password){
        JSONObject response = new JSONObject();

        try
        {
            client.setConnectTimeout(10000);

            client.connect(ipServer, 21);
            boolean login = client.login(user,password);

            JSONArray List = new JSONArray();

            if (login)
            {

                client.enterLocalPassiveMode();
//                client.changeWorkingDirectory(constantes.ruta_properties);

                // lists files and directories in the current working directory
                FTPFile[] files = client.listFiles();

                // iterates over the files and prints details for each
                DateFormat dateFormater = new SimpleDateFormat("dd-MM-yyy HH:mm:ss a");

                for (FTPFile file : files) {
                    String fname = file.getName();
                    if (fname.substring(fname.length()-11, fname.length()).equals(".properties")) {
                        JSONObject data = new JSONObject();

                        data.put("name", fname);
                        data.put("lastMod", dateFormater.format(file.getTimestamp().getTime()));

                        List.put(data);
//                        System.out.println(fname);
//                        System.out.println(dateFormater.format(file.getTimestamp().getTime()));
                    }
                }

                response.put("R", "0");
                response.put("M", "Todo correcto.");
                response.put("data", List);
            }
            else {
                System.out.println("Login Failed...");

                response.put("R", "1");
                response.put("M", "FTP: Usuario o Contraseña incorrectos.");
                response.put("data", new JSONArray());
            }

            client.logout();
            client.disconnect();
//            System.out.println("Disconnected");


            return response;
        }
        catch (SocketException e){
            e.printStackTrace();
            System.out.println("IP inalcanzable. Por favor, revise la VPN");

            response.put("M", "IP inalcanzable. Por favor, revise la VPN");
        }
        catch (SocketTimeoutException e){
            e.printStackTrace();
            System.out.println("Conexión TimeOut... Por favor, revise la VPN");

            response.put("M", "Conexión TimeOut... Por favor, revise la VPN");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Intento fallido al conectar con el servidor, trata nuevamente.");

            response.put("M", "Intento fallido al conectar con el servidor, trata nuevamente.");
        }

        response.put("R", "1");
        response.put("data", new JSONArray());

        return response;
    }

    public JSONObject getDataPropertie (FTPClient client, String ipServer, String user, String password, String fileName){
        JSONObject response = new JSONObject();

        try
        {
            client.setConnectTimeout(10000);

            client.connect(ipServer, 21);
            boolean login = client.login(user,password);
            if (login)
            {

                client.enterLocalPassiveMode();
//                client.changeWorkingDirectory(constantes.ruta_properties);

                System.out.printf(fileName);
                InputStream inputStream = client.retrieveFileStream(fileName);
                Scanner sc = new Scanner(inputStream);

//                JSONArray List = new JSONArray();

                String data = "";
                //Reading the file line by line and printing the same
                while (sc.hasNextLine()) {
                    data += sc.nextLine()+"\n";
//                    if (line.length() > 0 && !line.substring(0,1).equals("#")){
//                        String[] split = line.split("=");
//                        JSONObject propertie = new JSONObject();
//                        propertie.put("name", split[0]);
//                        propertie.put("value", split[1].trim());

//                        List.put(propertie);
//                    }
                }

                sc.close();
                inputStream.close();

                response.put("R", "0");
                response.put("M", "Todo correcto.");
                response.put("data", data);

                return response;
            }
            else {
                System.out.println("Login Failed...");

                response.put("R", "1");
                response.put("M", "FTP: Usuario o Contraseña incorrectos.");
                response.put("data", "");
            }


            client.logout();
            client.disconnect();
//            System.out.println("Disconnected");
        }
        catch (SocketException e){
            e.printStackTrace();
            System.out.println("IP inalcanzable. Por favor, conectate a la VPN.");

            response.put("M", "IP inalcanzable. Por favor, conectate a la VPN.");
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
        response.put("data", "");

        return response;
    }

    public JSONObject setDataPropertie (FTPClient client, String ipServer, String user, String password, String remoteFileName, String localFileName){
        JSONObject response = new JSONObject();

        try
        {
            client.setConnectTimeout(10000);

            client.connect(ipServer, 21);
            boolean login = client.login(user,password);
            if (login)
            {

                System.out.printf("remote: "+remoteFileName);
                System.out.printf("local: " +localFileName);

                client.enterLocalPassiveMode();

//                client.changeWorkingDirectory(constantes.ruta_properties);

                File firstLocalFile = new File(localFileName);

                InputStream inputLocal = new FileInputStream(firstLocalFile);
                boolean done = client.storeFile(remoteFileName, inputLocal);
                inputLocal.close();

                if (done) {
                    response.put("R", "0");
                    response.put("M", "Archivo actualizado correctamente.");
//                    System.out.println("The file is uploaded successfully.");
                    firstLocalFile.delete();
                }
                else {
                    response.put("R", "1");
                    response.put("M", "Ocurrio un error al actualizar el archivo.");
//                    System.out.println("The file is uploaded successfully.");
                }


                return response;
            }
            else {
                System.out.println("Login Failed...");

                response.put("R", "1");
                response.put("M", "FTP: Usuario o Contraseña incorrectos.");
            }


            client.logout();
            client.disconnect();
//            System.out.println("Disconnected");
        }
        catch (SocketException e){
            e.printStackTrace();
            System.out.println("IP inalcanzable. Por favor, conectate a la VPN.");

            response.put("M", "IP inalcanzable. Por favor, conectate a la VPN.");
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
