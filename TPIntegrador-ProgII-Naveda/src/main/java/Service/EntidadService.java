package Service;

import java.sql.SQLException;
import java.util.List;

public interface EntidadService<T> {
    List<T> listarAll() throws SQLException;
    T listarById(Long id) throws SQLException;
    void eliminar(Long id) throws SQLException;
}
