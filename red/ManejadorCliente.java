package red;

import java.io.*;
import java.net.Socket;
import modelo.*;
import servicios.*;

public class ManejadorCliente implements Runnable{
    private Socket socket;
    private Restaurante restaurante;
    private Servidor servidor;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public ManejadorCliente(Socket socket, Restaurante restaurante, Servidor servidor){
        this.socket = socket;
        this.restaurante = restaurante;
        this.servidor = servidor;

        try{
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            System.out.println("Error al crear flujos: " + e.getMessage());
        }
    }
    public void enviarMensaje(Mensaje m){
        try{
            salida.writeObject(m);
            salida.flush(); 
        }catch(IOException e){
            System.out.println("Error al mandar mensaje: " + e.getMessage());
        }
    }
    @Override  
        public void run(){
            try{
              while(true){
                Mensaje mensaje = (Mensaje) entrada.readObject();
                System.out.println("Mensaje recibido: " + mensaje.getTipo());

                switch (mensaje.getTipo()) {
                    case NUEVO_PEDIDO:
                        restaurante.agregarPedidoMesa(mensaje.getNumMesa(), mensaje.getPedido());
                        ProcesarPedido procesar = new ProcesarPedido(mensaje.getPedido());
                        Thread hiloCocina = new Thread(procesar);
                        hiloCocina.start();
                        servidor.difusion(mensaje);
                        break;
                    case PEDIDO_LISTO:
                        Mensaje listo = new Mensaje(Mensaje.tipoMensaje.PEDIDO_LISTO, mensaje.getPedido(), mensaje.getNumMesa(), mensaje.getDescripcion());
                        servidor.difusion(listo);                        
                        break;

                    case PEDIR_CUENTA:
                        Mesa mesa = restaurante.obtenerMesa(mensaje.getNumMesa());
                        double total = mesa.TotalCuenta();
                        enviarMensaje(new Mensaje(Mensaje.tipoMensaje.PEDIR_CUENTA, mensaje.getPedido(), mensaje.getNumMesa(), "Total a pagar: " + total));
                        break;
                    case PAGAR:
                        Mesa mesaPagar = restaurante.obtenerMesa(mensaje.getNumMesa());
                        mesaPagar.limpiarMesa();
                        servidor.difusion(new Mensaje(Mensaje.tipoMensaje.PAGAR, mensaje.getPedido(), mensaje.getNumMesa(), "Mesa pagada"));
                       // mandar el mensaje de que se limpio
                        break;
                    case CIERRE_CAJA:
                        CierreCaja.generarCierre(restaurante); //Explicar al profe porque pregunte a la IA sobre lo de static
                        enviarMensaje(new Mensaje(Mensaje.tipoMensaje.INFO, null, 0, "Cierre de caja correcto."));
                        break;
                    case INFO:
                        System.out.println("MSG DEL CLI: " + mensaje.getDescripcion());
                        break;
                    default:
                        System.out.println("Mensaje no reconocido.");
                }
            }
        } catch (Exception e) {
            System.out.println("Cliente desconectado o error");
        } finally {
            try {
                socket.close();
                servidor.removeCliente(this);
            } catch (IOException e) {
                System.out.println("Error al cerrar socket");
            }
        }
    }
        
}
