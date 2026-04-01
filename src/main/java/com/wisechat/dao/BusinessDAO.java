package com.wisechat.dao;

import com.wisechat.model.Business;
import java.util.List;

/**
 * Interfaz BusinessDAO que define las operaciones CRUD
 * requeridas para la tabla BUSINESS por el SENA.
 */
public interface BusinessDAO {
    void insertarNegocio(Business negocio);
    List<Business> listarNegocios();
    Business consultarNegocio(int id);
    void actualizarNegocio(Business negocio);
    void eliminarNegocio(int id);
}
