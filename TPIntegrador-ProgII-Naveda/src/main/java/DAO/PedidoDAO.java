package DAO;

import Config.DatabaseConnection;
import Entity.Categoria;
import Entity.DetallePedido;
import Entity.Estado;
import Entity.FormaPago;
import Entity.Pedido;
import Entity.Producto;
import Entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements EntidadDAO<Pedido> {

    private final String SELECT_ALL_SQL = "SELECT * FROM pedido WHERE eliminado = FALSE";
    private final String SELECT_ID_SQL = "SELECT * FROM pedido WHERE eliminado = FALSE AND id = ?";
    private final String DELETE_ID_SQL = "UPDATE pedido SET eliminado = TRUE WHERE id = ?";
    private final String INSERT_SQL = "INSERT INTO pedido(usuario_id, estado, forma_pago, total, eliminado) VALUES(?, ?, ?, ?, ?)";       
    private final String INSERT_DP_SQL = "INSERT INTO detalle_pedido(pedido_id, producto_id, cantidad, total) VALUES (?, ?, ?, ?)";
    private final String UPDATE_ESTADO_SQL = "UPDATE pedido SET estado = ? WHERE id = ?";       
    private final String UPDATE_FORMAPAGO_SQL = "UPDATE pedido SET forma_pago = ? WHERE id = ?";       
    private final String SELECT_DETALLES_SQL = "SELECT dp.*,"
            + " p.id AS producto_id,"
            + " p.nombre AS producto_nombre,"
            + " p.precio AS producto_precio,"
            + " p.descripcion AS producto_descripcion,"
            + " p.stock AS producto_stock,"
            + " p.imagen AS producto_imagen,"
            + " p.disponible AS producto_disponible,"
            + " p.eliminado AS producto_eliminado,"
            + " c.id AS categoria_id,"
            + " c.nombre AS categoria_nombre,"
            + " c.descripcion AS categoria_descripcion,"
            + " c.eliminado AS categoria_eliminado,"
            + " c.created_at AS categoria_created_at"
            + " FROM detalle_pedido dp"
            + " JOIN producto p ON dp.id_producto = p.id"
            + " JOIN categoria c ON p.id_categoria = c.id"
            + " WHERE dp.id_pedido = ?"; 
    private final String SELECT_USUARIO_SQL = "SELECT * FROM usuario WHERE eliminado = FALSE AND id = ?";
    
    @Override
    public List<Pedido> selectAll() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_SQL); ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                pedidos.add(mapPedido(rs));
            }
            return pedidos;
        }
               
    }

    @Override
    public Pedido selectById(Long id) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ID_SQL)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                  return mapPedido(rs);
                } 
            }  
        }
        return null;
    }    
    
    @Override
    public void eliminar(Long id) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(DELETE_ID_SQL)){
            ps.setLong(1, id);
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun pedido con el ID " + id);
            }
        }
    }
    
 
    public Long insert(Pedido pedido) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)){
            ps.setLong(1, pedido.getUsuario().getId());
            ps.setString(2, pedido.getEstado().name());
            ps.setString(3, pedido.getFormaPago().name());
            ps.setDouble(4, pedido.getTotal());
            ps.setBoolean(5, pedido.isEliminado());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
    }
    
    public void insertDetallePedido(Long id_pedido, DetallePedido dp) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_DP_SQL)){
            ps.setLong(1, id_pedido);
            ps.setLong(2, dp.getProducto().getId());
            ps.setLong(3, dp.getCantidad());
            ps.setDouble(4, dp.calcularTotal());
            ps.executeUpdate();
        }
    }
    
    public void updateEstado(Long id, Estado estado) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_ESTADO_SQL)){
            ps.setString(1, estado.name());
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun pedido con el ID " + id);
            }
            
        }
    }
    
    public void updateFormaPago(Long id, FormaPago formaPago) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_FORMAPAGO_SQL)){
            ps.setString(1, formaPago.name());
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun pedido con el ID " + id);
            }
            
        }
    }
    
    private Pedido mapPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();

        pedido.setUsuario(selectUsuarioById(rs.getLong("id_usuario")));
        pedido.setId(rs.getLong("id"));
        pedido.setEstado(Estado.valueOf(rs.getString("estado")));
        pedido.setFormaPago(FormaPago.valueOf(rs.getString("forma_pago")));
        pedido.setDetalles(selectDetallesPedidoById(rs.getLong("id")));
        pedido.setTotal(rs.getDouble("total"));
        pedido.setEliminado(rs.getBoolean("eliminado"));
        pedido.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

        return pedido;
    }
    
    private Usuario selectUsuarioById(Long id) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_USUARIO_SQL)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Usuario usuario = new Usuario();
                
                usuario.setId(rs.getLong("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setMail(rs.getString("mail"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setEliminado(rs.getBoolean("eliminado"));
                usuario.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

                return usuario;
            }
        }
    }
    
    private List<DetallePedido> selectDetallesPedidoById(Long id) throws SQLException{
        List<DetallePedido> detalles = new ArrayList<>();
        
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_DETALLES_SQL)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {                    
                    detalles.add(mapDetallePedido(rs));
                }
            }  
        }
        return detalles;
    }
    
    private DetallePedido mapDetallePedido(ResultSet rs) throws SQLException {
        DetallePedido dp = new DetallePedido(); 
        
        dp.setCantidad(rs.getInt("cantidad"));
        dp.setProducto(mapProducto(rs));
        
        return dp;       
    }
    
    private Producto mapProducto(ResultSet rs) throws SQLException {
        Producto prod = new Producto();

        prod.setId(rs.getLong("producto.id"));
        prod.setNombre(rs.getString("producto.nombre"));
        prod.setPrecio(rs.getDouble("producto.precio"));
        prod.setDescripcion(rs.getString("producto.descripcion"));
        prod.setStock(rs.getInt("producto.stock"));
        prod.setImagen(rs.getString("producto.imagen"));
        prod.setDisponible(rs.getBoolean("producto.disponible"));
        prod.setEliminado(rs.getBoolean("producto.eliminado"));
        prod.setCreatedAt(rs.getObject("producto.created_at", LocalDateTime.class));
        
        //Reconstruir categoria
        Categoria cat = new Categoria();
        cat.setId(rs.getLong("categoria.id"));
        cat.setNombre(rs.getString("categoria.nombre"));
        cat.setDescripcion(rs.getString("categoria.descripcion"));
        cat.setEliminado(rs.getBoolean("categoria.eliminado"));
        cat.setCreatedAt(rs.getObject("categoria.created_at", LocalDateTime.class));
        
        prod.setCategoria(cat);

        return prod;
    }
}
