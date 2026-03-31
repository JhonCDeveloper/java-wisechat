package com.wisechat.dao;

import com.wisechat.model.Question;
import java.util.List;

/**
 * Interfaz DAO para la entidad QUESTIONS.
 */
public interface QuestionDAO {
    void crear(Question question);
    List<Question> listarTodo();
    Question buscarPorId(int id);
    void actualizar(Question question);
    void eliminar(int id);
}
