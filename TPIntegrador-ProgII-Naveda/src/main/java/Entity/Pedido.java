package Entity;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido extends Entidad {
    private Usuario usuario;
    private Estado estado;
    private FormaPago formapago;
    private List<DetallePedido> detalles;
    private double total;
    
    //CONSTRUCTORES

    public Pedido(Usuario usuario, Estado estado, FormaPago formapago, List<DetallePago> detalles, double total, long id, boolean eliminado, LocalDateTime createdAt) {
        super(id, eliminado, createdAt);
        this.usuario = usuario;
        this.estado = estado;
        this.formapago = formapago;
        this.detalles = detalles;
        this.total = total;
    }

    public Pedido(Usuario usuario, Estado estado, FormaPago formapago, List<DetallePago> detalles, double total, long id) {
        super(id);
        this.usuario = usuario;
        this.estado = estado;
        this.formapago = formapago;
        this.detalles = detalles;
        this.total = total;
    }
    
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

    public FormaPago getFormapago() {
        return formapago;
    }

    public void setFormapago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
