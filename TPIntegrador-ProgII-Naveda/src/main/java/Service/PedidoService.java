package Service;

import DAO.PedidoDAO;
import Entity.DetallePedido;
import Entity.Estado;
import Entity.FormaPago;
import Entity.Pedido;
import Entity.Producto;
import Entity.Usuario;
import java.sql.SQLException;
import java.util.List;

public class PedidoService implements EntidadService<Pedido> {
    private final PedidoDAO pedidoDAO;
    
    public PedidoService(PedidoDAO pedidoDAO) {
        if (pedidoDAO != null) {
            this.pedidoDAO = pedidoDAO;
        } else throw new IllegalArgumentException("El pedidoDAO pasado a PedidoService no puede ser null");
    }
    
    //METODOS
    
    public Long crear(Usuario usuario, Estado estado, FormaPago formaPago, double total, boolean eliminado) throws SQLException {
        Pedido pedido = new Pedido();
               
        pedido.setUsuario(usuario);
        pedido.setEstado(estado);
        pedido.setFormaPago(formaPago);
        pedido.setTotal(total);
        pedido.setEliminado(eliminado);

        return pedidoDAO.insert(pedido); 
    }
    
    public void crearDetallePedido(Long id_pedido, Producto producto, int cantidad) throws SQLException {
        validarCantidad(cantidad);
        
        DetallePedido dp = new DetallePedido();
        
        dp.setProducto(producto);
        dp.setCantidad(cantidad);
        
        pedidoDAO.insertDetallePedido(id_pedido, dp);
    }
    
    public void editarEstado(long id, Estado estado) throws SQLException {
        validarId(id);
        
        pedidoDAO.updateEstado(id, estado);        
    }
    
    public void editarFormaPago(long id, FormaPago formaPago) throws SQLException {
        validarId(id);
        
        pedidoDAO.updateFormaPago(id, formaPago);        
    }
    
    @Override
    public List<Pedido> listarAll() throws SQLException{
        return pedidoDAO.selectAll();
    }

    @Override
    public Pedido listarById(Long id) throws SQLException {
        validarId(id);
        
        Pedido pedido = pedidoDAO.selectById(id);
        
        if(pedido == null) throw new IllegalArgumentException("No existe ningun pedido con ese Id");
        
        return pedido;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        validarId(id);
        
        pedidoDAO.eliminar(id);        
    }
        
    private void validarId(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        if (id <= 0) throw new IllegalArgumentException("El id no puede ser negativo");        
    }
    
    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad no puede ser negativa");        
    }
    
}
