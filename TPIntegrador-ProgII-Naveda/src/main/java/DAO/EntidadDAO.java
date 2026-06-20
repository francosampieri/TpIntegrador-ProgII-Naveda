package DAO;

import java.util.List;

public interface EntidadDAO<T> {
    List<T> selectAll();
    T selectById();
    void eliminar(Long id);
}
