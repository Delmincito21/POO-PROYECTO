package red;

import java.io.IOException;
import java.net.*;
import java.util.*;
import modelo.*;

public class Servidor {
    private final static  int puerto = 8080; // Esto puede cambiar bro (Pd: va a cambiar)
    private Restaurante restaurante;
    private List<ManejadorCliente> clientes;
    
    
    public Servidor() {
        restaurante = new Restaurante(10); //10 mesas para probar, puede cambiar
        clientes = new ArrayList<>();
    }

    public void iniciar(){   
        
        try(ServerSocket servidor = new ServerSocket(puerto)){
            System.out.println("El servidor está corriendo correctamente en el puerto " + puerto);
            
            while(true){
                System.out.println("Esperando clientes");
                Socket socketCliente = servidor.accept();
                System.out.println("Cliente conectado desde: " +  socketCliente.getInetAddress());

                ManejadorCliente manejador = new ManejadorCliente(socketCliente,restaurante,this);
                clientes.add(manejador);

                Thread hilo = new Thread(manejador);
                hilo.start();
            }
        }catch(IOException e){
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
    public void difusion(Mensaje m){
        synchronized (clientes){
            for(ManejadorCliente cli : clientes){
                cli.enviarMensaje(m);
            }
        }
    }
    public void removeCliente(ManejadorCliente cliente){
        clientes.remove(cliente);
    }
   public static void main(String[] args) {
    Servidor servidor = new Servidor();
    servidor.iniciar();
   }
    
}
