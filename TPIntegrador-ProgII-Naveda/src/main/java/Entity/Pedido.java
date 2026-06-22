package Entity;

import java.util.List;

public class Pedido extends Entidad implements Calculable{
    private Usuario usuario;
    private Estado estado;
    private FormaPago formapago;
    private List<DetallePedido> detalles;
    private double total;
    
    //GETTERS SETTERS

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public FormaPago getFormaPago() {
        return formapago;
    }

    public void setFormaPago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
        actualizarTotal();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    //METODOS
    
    public void addDetallePedido(DetallePedido dp) {
        this.detalles.add(dp);
        actualizarTotal();
    }
    
    public void removeDetallePedido(DetallePedido dp) {
        this.detalles.remove(dp);
        actualizarTotal();
    }

    @Override
    public double calcularTotal() {
        double total = 0;
        for(DetallePedido d: detalles) {
            total += d.calcularTotal();
        }
        
        return total;
    }
    
    public void actualizarTotal() {
        this.total = calcularTotal();
    }
    
}
