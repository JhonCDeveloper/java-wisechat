package com.wisechat.dao;

import com.wisechat.model.Form;
import java.util.List;

/**
 * Interfaz DAO para la entidad FORM.
 */
public interface FormDAO {
    void crear(Form form);
    List<Form> listarTodo();
    Form buscarPorId(int id);
    void actualizar(Form form);
    void eliminar(int id);
}
