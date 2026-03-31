package com.wisechat.dao;

import com.wisechat.model.Message;
import java.util.List;

/**
 * Interfaz DAO para la entidad MESSAGES.
 */
public interface MessageDAO {
    void crear(Message message);
    List<Message> listarTodo();
    Message buscarPorId(int id);
    void actualizar(Message message);
    void eliminar(int id);
}
