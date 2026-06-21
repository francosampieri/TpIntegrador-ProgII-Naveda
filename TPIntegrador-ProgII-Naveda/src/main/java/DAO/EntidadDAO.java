package DAO;

import java.sql.SQLException;
import java.util.List;

public interface EntidadDAO<T> {
    List<T> selectAll() throws SQLException;
    T selectById(Long id) throws SQLException;
    void eliminar(Long id) throws SQLException;
}
