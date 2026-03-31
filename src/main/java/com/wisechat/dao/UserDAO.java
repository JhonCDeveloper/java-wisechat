package com.wisechat.dao;

import com.wisechat.model.User;
import java.util.List;

/**
 * Interfaz DAO para la entidad USER.
 * Define el contrato CRUD obligatorio.
 */
public interface UserDAO {
    void crear(User user);
    List<User> listarTodo();
    User buscarPorId(int id);
    void actualizar(User user);
    void eliminar(int id);
}
