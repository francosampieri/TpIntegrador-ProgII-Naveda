package DAO;

import Config.DatabaseConnection;
import Entity.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements EntidadDAO<Categoria> {

    private final String SELECT_ALL_SQL = "SELECT * FROM categoria WHERE eliminado = FALSE";
    private final String SELECT_ID_SQL = "SELECT * FROM categoria WHERE eliminado = FALSE AND id = ?";
    private final String DELETE_ID_SQL = "UPDATE categoria SET eliminado = TRUE WHERE id = ?";
    private final String SELECT_NOMBRE_SQL = "SELECT * FROM categoria WHERE nombre = ?";
    private final String INSERT_SQL = "INSERT INTO categoria(nombre, descripcion) VALUES(?, ?)";       
    private final String UPDATE_NOMBRE_SQL = "UPDATE categoria SET nombre = ? WHERE id = ?";       
    private final String UPDATE_DESCRIPCION_SQL = "UPDATE categoria SET descripcion = ? WHERE id = ?";       
    
    @Override
    public List<Categoria> selectAll() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_SQL); ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                categorias.add(mapCategoria(rs));
            }
            return categorias;
        }
               
    }

    @Override
    public Categoria selectById(Long id) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ID_SQL)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                  return mapCategoria(rs);
                } 
            }  
        }
        return null;
    }

    public Categoria selectByNombre(String nombre) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_NOMBRE_SQL)){
         ps.setString(1, nombre);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapCategoria(rs);
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
                throw new SQLException("No se encontró ninguna categoria con el ID " + id);
            }
        }
    }
    
 
    public Long insert(Categoria cat) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, cat.getNombre());
            ps.setString(2, cat.getDescripcion());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
    }
    
    public void updateNombre(Long id, String nombre) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_NOMBRE_SQL)){
            ps.setString(1, nombre);
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ninguna categoria con el ID " + id);
            }
            
        }
    }
    
    public void updateDescripcion(Long id, String descripcion) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_DESCRIPCION_SQL)){
            ps.setString(1, descripcion);
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ninguna categoria con el ID " + id);
            }
            
        }
    }
    
    private Categoria mapCategoria(ResultSet rs) throws SQLException {
        Categoria cat = new Categoria();

        cat.setId(rs.getLong("id"));
        cat.setNombre(rs.getString("nombre"));
        cat.setDescripcion(rs.getString("descripcion"));
        cat.setEliminado(rs.getBoolean("eliminado"));
        cat.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

        return cat;
    }
    

}
