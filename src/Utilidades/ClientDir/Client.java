package Utilidades.ClientDir;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class Client {

    private Selector selector_client;
    private  int operations = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
    public ArrayList<ByteBuffer> list = new ArrayList<>();
    private String IP_CLIENT;
    private int PORT_CLIENT;
    private SocketChannel channel;
    private boolean restart = false;
    private JSONObject response;
    public boolean respuesta = false;

    public Client(String IP, int PORT) {
        this.IP_CLIENT = IP;
        this.PORT_CLIENT = PORT;
    }

    public static Client getInstance() {
        return NewSingletonHolder.INSTANCE;
    }

    private static class NewSingletonHolder {
        private static final Client INSTANCE = new Client("localhost", 23315);
    }

    public void startClient () throws Exception
    {

        selector_client = Selector.open();

        channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(IP_CLIENT, PORT_CLIENT));
        channel.register(selector_client, operations);


        while (true) {
            if (selector_client.select() > 0) {
                try {

                    if(restart){
                        restart = false;
                        System.out.println("Hizo el break");
                        break;
                    }
                    boolean doneStatus = processReadySet(selector_client.selectedKeys());
                    if (doneStatus) {
                        Thread.sleep(10000);
                        SocketChannel channel2 = SocketChannel.open();
                        channel2.configureBlocking(false);
                        channel2.connect(new InetSocketAddress(IP_CLIENT, PORT_CLIENT));
                        channel2.register(selector_client, operations);


//                        System.out.println(" \n\n STATUS DONE!!\n\n ");

//                        break;
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void stopclient() throws IOException
    {
        channel.socket().close();

    }

    public  boolean processReadySet(Set readySet) throws Exception
    {
        Iterator iterator = readySet.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = (SelectionKey) iterator.next();
            iterator.remove();

            if (key.isValid() && key.isConnectable()) {

                boolean connected = processConnect(key);
                if (!connected) {

                    System.out.println(" \n\n Conexion cerrada \n\n");
                    return true;
                    // Exit
                }
                else {

                    SocketChannel socketChannel = (SocketChannel) key.channel();
//                    System.out.println(socketChannel.socket().isConnected());
                    System.out.println("Conexion activa.");
                }

            }

            if (key.isValid() && key.isReadable()) {

                // Cuando se cae la conexion siempre se queda aca y nunca entra en el if
                if (!processRead(key)){
                    return true;
                }


            }
            else if (key.isValid() && key.isWritable())
            {

                if (list.size() > 0  ) {

                    int i =0;
                    int size = list.size();
                    while ( i < size && size > 0) {
                        try {
                                SocketChannel socketChannel = (SocketChannel) key.channel();
                                if(socketChannel.isConnected())
                                {

                                    if(list.size()> 0) {
//                                        System.out.println("Tamano lista de mensajes: " + list.size());
                                        socketChannel.write(list.get(i));
                                        list.remove(i);
                                        size--;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                else {

                                   socketChannel.close();
                                    System.out.println(" \n\n FALLA DE SOCKET!!\n\n ");
                                   break;
                                }
                        }catch (NullPointerException ex){
                            ex.printStackTrace();
                        }
                        catch (ClosedByInterruptException ex){
                            ex.printStackTrace();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }


            }


        }
        return false; // Not done yet
    }

    // Cuando se cae la conexcion esto siempre retorna TRUE
    public  boolean processRead(SelectionKey key)
    {
        try {

            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(2048);

            int sizeRead;
            sizeRead = socketChannel.read(buffer);
            System.out.println("[CLIENTE] Tamano del mensaje: " + sizeRead);

            if (sizeRead > 0) {
                byte[] datarcv = new byte[sizeRead];
                System.arraycopy(buffer.array(), 0, datarcv, 0, sizeRead);

                JSONObject data = new JSONObject(new String(datarcv));

//                System.out.println("[CLIENTE] recibe: " + data.toString(1));

//                 RESPUESTA SERVIDOR
                this.setResponse(data);
                this.respuesta = true;

                buffer.flip();
            }

            if (sizeRead == -1){
                Socket socket = socketChannel.socket();
                SocketAddress remoteAddr = socket.getRemoteSocketAddress();
                System.out.println("Conexion cerrada por cliente");


                socketChannel.close();
                return false;
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Servidor caido.");
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public  boolean processConnect(SelectionKey key) throws Exception
    {
        SocketChannel channel = (SocketChannel) key.channel();

        while (channel.isConnectionPending()) {

            try {
//                System.out.println(channel.isConnected());
                channel.finishConnect();

            }
            catch (ConnectException e){
//                System.out.println("Reconectando...");


//                channel = (SocketChannel) key.channel();
//                channel.register(key.selector(), SelectionKey.OP_CONNECT);
                return false;
//                e.printStackTrace();
            }
            catch (Exception e){
//                e.printStackTrace();
            }


        }
        return true;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }
}