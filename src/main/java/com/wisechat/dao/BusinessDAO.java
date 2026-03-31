package com.wisechat.dao;

import com.wisechat.model.Business;
import java.util.List;

/**
 * Interfaz DAO para la entidad BUSINESS.
 */
public interface BusinessDAO {
    void crear(Business business);
    List<Business> listarTodo();
    Business buscarPorId(int id);
    void actualizar(Business business);
    void eliminar(int id);
}
