package red;

import java.io.*;
import java.net.*;

public class Cliente {
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private String host;
    private int puerto;
    private boolean isConectado;

    public Cliente(String host, int puerto){
        this.host = host;
        this.puerto = puerto;
        this.isConectado = false;
    }
    
    public boolean isConectado() { return isConectado;}


    public boolean conectar(){
        try{
            cerrarConexion();
            
        socket = new Socket(host,puerto);
        salida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());
        isConectado = true;
        System.out.println("Conectado al servidor correctamente");
        return true;

    }catch(IOException e ){
        System.out.println("Error al crear el cliente: " + e.getMessage());
        isConectado =false;
        return false;
    }
}

public  synchronized boolean enviarMsgServidor(Mensaje m){
    if(!isConectado){
        System.out.println("No hay conexión con el servidor.");
        return false;
    }
    try{
        salida.writeObject(m);
        salida.flush();
        return true;
    }catch(IOException e){
        System.out.println("Error al enviar mensaje: " + e.getMessage());
        isConectado = false;
        return false;
    }
}
public Mensaje recibirMsgServidor() throws ClassNotFoundException{ // profe ese thowr lo puso el bombillo
    if(!isConectado){
        System.out.println("No hay conexión con el servidor.");
        return null;
    }
    try{
        return (Mensaje) entrada.readObject();
    }catch(IOException e){
        System.out.println("Error al recibir mensaje: "+ e.getMessage());
        return null;
    }

}
public void cerrarConexion(){
    try{
        isConectado = false;
        if(entrada != null){ entrada.close();}
        if(salida != null){ salida.close();}

        if(socket != null){socket.close();}

    }catch(IOException e){
        System.out.println("Error al cerrar conexíon: "+ e.getMessage());

    }
}
public static void main(String[] args) {   
        final  String HOST ="127.0.0.1";
        final int puerto = 8080;
        Cliente cliente = new Cliente(HOST, puerto);
        cliente.conectar();
        if(cliente.isConectado){
            System.out.println("Cliente listo");

            Mensaje msg = new Mensaje(Mensaje.tipoMensaje.INFO, null, 0, "SOY el cliente");
            cliente.enviarMsgServidor(msg);
            cliente.cerrarConexion();
        }
    
        
    }
}
