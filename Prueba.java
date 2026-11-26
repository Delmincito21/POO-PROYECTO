import java.io.*;
import java.net.Socket;
import modelo.*;
import red.Mensaje;

public class Prueba {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 8080);
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            System.out.println("Conectado al servidor correctamente.");

            // 1. Crear una bebida de prueba (con alcohol)
            Bebida cerveza = new Bebida("PED-01", 3, "Presidente Fría", 250, true);
            System.out.println("Total calculado bebida (con Impuestos): " + cerveza.calcularTotal());

            // 2. Enviar NUEVO_PEDIDO al servidor
            Mensaje msgPedido = new Mensaje(Mensaje.tipoMensaje.NUEVO_PEDIDO, cerveza, 3, "Nuevo pedido mesa 3");
            salida.writeObject(msgPedido);
            salida.flush();
            System.out.println("Pedido enviado.");

            // Escuchar respuesta del servidor (difusión)
            Mensaje respuesta1 = (Mensaje) entrada.readObject();
            System.out.println("Respuesta del servidor: " + respuesta1.getTipo() + " Desc: " + respuesta1.getDescripcion());

            // 3. Pedir la cuenta
            salida.writeObject(new Mensaje(Mensaje.tipoMensaje.PEDIR_CUENTA, null, 3, "Cuenta mesa 3"));
            salida.flush();
            Mensaje respuestaCuenta = (Mensaje) entrada.readObject();
            System.out.println("Cuenta recibida: " + respuestaCuenta.getDescripcion());

            // 4. Pagar
            salida.flush();
            salida.writeObject(new Mensaje(Mensaje.tipoMensaje.PAGAR, null, 3, "Pago mesa 3"));
            Mensaje respuestaPago = (Mensaje) entrada.readObject();
            System.out.println("Confirmación pago: " + respuestaPago.getDescripcion());

            // 5. Generar cierre de caja (cierreCaja.txt)
            salida.writeObject(new Mensaje(Mensaje.tipoMensaje.CIERRE_CAJA, null, 0, "Cerrar caja"));
            salida.flush();
            System.out.println("Solicitud de cierre enviada.");

        } catch (Exception e) {
            System.out.println("Error en la prueba: " + e.getMessage());
        }
    }
}
