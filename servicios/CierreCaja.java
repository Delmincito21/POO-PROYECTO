package servicios;

import modelo.*;
import java.time.*;
import java.io.*;
public class CierreCaja {
    
    public static void generarCierre(Restaurante restaurante){
        String nombreArchivo = "cierreCaja.txt";
        try(PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))){
            writer.println("====CIERRE DE CAJA====");
            writer.println("Fecha:  " + LocalDateTime.now());
            writer.println("--------------------------------");

            double totalGeneral =0;

            for(Pedido p: restaurante.getHistorialPedidos()){
                writer.println("Mesa : " + p.getNumMesa() + " Total: " + p.calcularTotal());
                totalGeneral += p.calcularTotal();
            }
            writer.println("--------------------------------");
            writer.println("Total General: " + totalGeneral);
            System.out.println("Archivo generado: " + nombreArchivo);
        }catch(Exception e){
            System.out.println("Errol al crear el archivo: "+ e.getMessage());
        }
    }
}
