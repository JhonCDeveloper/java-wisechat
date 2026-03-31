package com.wisechat.dao;

import com.wisechat.model.ActionsPlan;
import java.util.List;

/**
 * Interfaz DAO para la entidad ACTIONS_PLAN.
 */
public interface PlanAccionDAO {
    void crear(ActionsPlan plan);
    List<ActionsPlan> listarTodo();
    ActionsPlan buscarPorId(int id);
    void actualizar(ActionsPlan plan);
    void eliminar(int id);
}
