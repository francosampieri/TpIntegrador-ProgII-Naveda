package DAO;

import Config.DatabaseConnection;
import Entity.Categoria;
import Entity.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements EntidadDAO<Producto> {

    private final String DELETE_ID_SQL = "UPDATE producto SET eliminado = TRUE WHERE id = ?";
    private final String SELECT_PRODUCTO_CON_CATEGORIA = "SELECT p.*, " +
    "c.id AS cat_id, " +
    "c.nombre AS cat_nombre, " +
    "c.descripcion AS cat_descripcion, " +
    "c.eliminado AS cat_eliminado, " +
    "c.created_at AS cat_created_at " +
    "FROM producto p " +
    "JOIN categoria c ON p.categoria_id = c.id " +
    "WHERE p.eliminado = FALSE";
    private final String SELECT_PRODUCTO_CON_CATEGORIA_POR_CAT = SELECT_PRODUCTO_CON_CATEGORIA + " AND p.categoria_id = ?";
    private final String SELECT_PRODUCTO_CON_CATEGORIA_ID = SELECT_PRODUCTO_CON_CATEGORIA + " AND p.id = ?";
    private final String INSERT_SQL = "INSERT INTO producto(nombre, descripcion, precio, stock, imagen, disponible, categoria_id, eliminado) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";       
    private final String UPDATE_PRECIO_SQL = "UPDATE producto SET precio = ? WHERE id = ?";       
    private final String UPDATE_STOCK_SQL = "UPDATE producto SET stock = ? WHERE id = ?";       
    private final String UPDATE_ID_CATEGORIA_SQL = "UPDATE producto SET categoria_id = ? WHERE id = ?";       
    
    @Override
    public List<Producto> selectAll() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_PRODUCTO_CON_CATEGORIA); ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                productos.add(mapProducto(rs));
            }
            return productos;
        }
               
    }

    @Override
    public Producto selectById(Long id) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_PRODUCTO_CON_CATEGORIA_ID)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                  return mapProducto(rs);
                } 
            }  
        }
        return null;
    }

    public List<Producto> selectByCategoria(Long id) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_PRODUCTO_CON_CATEGORIA_POR_CAT)){
         ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    productos.add(mapProducto(rs));
                }                 
            }  
        }  
        return productos;
    }
    
    
    @Override
    public void eliminar(Long id) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(DELETE_ID_SQL)){
            ps.setLong(1, id);
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun producto con el ID " + id);
            }
        }
    }
    
 
    public Long insert(Producto prod) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, prod.getNombre());
            ps.setString(2, prod.getDescripcion());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.setString(5, prod.getImagen());
            ps.setBoolean(6, prod.isDisponible());
            ps.setLong(7, prod.getCategoria().getId());
            ps.setBoolean(8, prod.isEliminado());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
    }
    
    public void updatePrecio(Long id, double precio) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_PRECIO_SQL)){
            ps.setDouble(1, precio);
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun producto con el ID " + id);
            }
            
        }
    }
    
    public void updateStock(Long id, int stock) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_STOCK_SQL)){
            ps.setInt(1, stock);
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun producto con el ID " + id);
            }
            
        }
    }
    
    public void updateIdCategoria(Long id, Long idCategoria) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_ID_CATEGORIA_SQL)){
            ps.setLong(1, idCategoria);
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun producto con el ID " + id);
            }
            
        }
    }
    
    private Producto mapProducto(ResultSet rs) throws SQLException {
        Producto prod = new Producto();

        prod.setId(rs.getLong("id"));
        prod.setNombre(rs.getString("nombre"));
        prod.setPrecio(rs.getDouble("precio"));
        prod.setDescripcion(rs.getString("descripcion"));
        prod.setStock(rs.getInt("stock"));
        prod.setImagen(rs.getString("imagen"));
        prod.setDisponible(rs.getBoolean("disponible"));
        prod.setEliminado(rs.getBoolean("eliminado"));
        prod.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        
        //Reconstruir categoria
        Categoria cat = new Categoria();
        cat.setId(rs.getLong("cat_id"));
        cat.setNombre(rs.getString("cat_nombre"));
        cat.setDescripcion(rs.getString("cat_descripcion"));
        cat.setEliminado(rs.getBoolean("cat_eliminado"));
        cat.setCreatedAt(rs.getObject("cat_created_at", LocalDateTime.class));
        
        prod.setCategoria(cat);

        return prod;
    }
    

}
