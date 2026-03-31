package com.wisechat.dao;

import com.wisechat.model.Response;
import java.util.List;

/**
 * Interfaz DAO para la entidad RESPONSES.
 */
public interface ResponseDAO {
    void crear(Response response);
    List<Response> listarTodo();
    Response buscarPorId(int id);
    void actualizar(Response response);
    void eliminar(int id);
}
