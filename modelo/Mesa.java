package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mesa implements Serializable {
    public enum EstadoMesa {LIBRE, OCUPADA, PENDIENTE_CUENTA}
    private int numMesa;
    private EstadoMesa estado;
    private List<Pedido> listaPedidos;
    
    public Mesa(int numMesa) {
        this.numMesa = numMesa;
        this.estado = EstadoMesa.LIBRE;
        this.listaPedidos = new ArrayList<>();
    }
    public int getNumMesa() {return numMesa;}
    public void setNumMesa(int numMesa) {this.numMesa = numMesa;}

    public EstadoMesa getEstado() {return estado;}
    public void setEstado(EstadoMesa estado) {this.estado = estado;}

    public List<Pedido> getListaPedidos() {return listaPedidos;}
    public void setListaPedidos(List<Pedido> listaPedidos) {this.listaPedidos = listaPedidos;}

    public void addPedido(Pedido pedido){
        if(pedido == null){
            throw new IllegalArgumentException("Pedido Inválido");
        }

        pedido.setNumMesa(numMesa);
        listaPedidos.add(pedido);
        estado = EstadoMesa.OCUPADA;
    }

    public double TotalCuenta(){
        double total =0;
        for(Pedido pedido : listaPedidos){
            total += pedido.calcularTotal();
        }
        return total;
    }
    public void limpiarMesa()
    {
        listaPedidos.clear();
        estado = EstadoMesa.LIBRE;
    }
}
