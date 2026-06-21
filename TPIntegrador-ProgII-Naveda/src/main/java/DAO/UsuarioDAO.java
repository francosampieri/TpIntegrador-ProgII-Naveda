package DAO;

import Config.DatabaseConnection;
import Entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements EntidadDAO<Usuario> {

    private final String SELECT_ALL_SQL = "SELECT * FROM usuario WHERE eliminado = FALSE";
    private final String SELECT_ID_SQL = "SELECT * FROM usuario WHERE eliminado = FALSE AND id = ?";
    private final String DELETE_ID_SQL = "UPDATE usuario SET eliminado = TRUE WHERE id = ?";
    private final String INSERT_SQL = "INSERT INTO usuario(nombre, apellido, mail, celular, eliminado) VALUES(?, ?)";       
    private final String UPDATE_NOMBRE_SQL = "UPDATE usuario SET nombre = ? WHERE id = ?";       
    private final String UPDATE_APELLIDO_SQL = "UPDATE usuario SET apellido = ? WHERE id = ?";       
    private final String UPDATE_MAIL_SQL = "UPDATE usuario SET mail = ? WHERE id = ?";       
    private final String UPDATE_CELULAR_SQL = "UPDATE usuario SET celular = ? WHERE id = ?";       
    
    @Override
    public List<Usuario> selectAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ALL_SQL); ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                usuarios.add(mapUsuario(rs));
            }
            return usuarios;
        }
               
    }

    @Override
    public Usuario selectById(Long id) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(SELECT_ID_SQL)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                  return mapUsuario(rs);
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
                throw new SQLException("No se encontró ningun usuario con el ID " + id);
            }
        }
    }
     
    public Long insert(Usuario usuario) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setBoolean(5, usuario.isEliminado());
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
                throw new SQLException("No se encontró ningun usuario con el ID " + id);
            }
            
        }
    }
    
    public void updateApellido(Long id, String apellido) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_APELLIDO_SQL)){
            ps.setString(1, apellido);
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun usuario con el ID " + id);
            }
            
        }
    }
    
    public void updateMail(Long id, String mail) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_MAIL_SQL)){
            ps.setString(1, mail);
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ningun usuario con el ID " + id);
            }
            
        }
    }
    
    public void updateCelular(Long id, String celular) throws SQLException {
        try(Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(UPDATE_CELULAR_SQL)){
            ps.setString(1, celular);
            ps.setLong(2, id);
            
            if(ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró ninguna categoria con el ID " + id);
            }
            
        }
    }
    
    
    
    private Usuario mapUsuario(ResultSet rs) throws SQLException {
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
