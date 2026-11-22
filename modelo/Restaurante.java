package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Pedido.EstadoPedido;

public class Restaurante implements Serializable {
private Map<Integer,Mesa> mesas;
private List<Pedido> historialPedidos;



public Restaurante(int cantMesas)
{
    this.mesas = new HashMap<>();
    this.historialPedidos = new ArrayList<>();

    for(int i=1; i <=cantMesas; i++){
        mesas.put(i, new Mesa(i));
    }
}

public Map<Integer,Mesa> getMesas() {
    return mesas;
}
public Mesa obtenerMesa(int numMesa){
    Mesa mesa = mesas.get(numMesa);
    if(mesa == null)
    {
        throw new IllegalArgumentException("La mesa numero "+ numMesa + " no existe en el restaurante.");
    }
    return mesa;
}
    public void agregarPedidoMesa(int numMesa, Pedido pedido){
        Mesa mesa = obtenerMesa(numMesa);
        mesa.addPedido(pedido);
        historialPedidos.add(pedido);
        System.out.println("Pedido agregado a la mesa "+ numMesa + " Descripción: " + pedido.getDescripcion());
    }

    
    public List<Pedido> getPedidosActivos(){
        List<Pedido> pedidosActivos = new ArrayList<>();

       for(Mesa mesa : mesas.values()){
        for(Pedido pedido : mesa.getListaPedidos()){
            if(pedido.getEstadoPedido() != EstadoPedido.ENTREGADO){
                pedidosActivos.add(pedido);
            }
        }
       }
       return pedidosActivos;
    }

        public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }
    public Pedido buscarPedido(String idPedido)
    {
        for(Pedido p : historialPedidos){
            if(p.getIdPedido().equals(idPedido)){
                return p;
            } 
        }
        throw new IllegalArgumentException("Pedido de ID: " + idPedido + " no existe.");
    }
}
