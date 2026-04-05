package com.wisechat.controller;

import com.wisechat.dao.UserDAO;
import com.wisechat.dao.UserDAOImpl;
import com.wisechat.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir al formulario de registro
        response.sendRedirect("registro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = "hash_basico_password"; // Contraseña por defecto

        User newUser = new User(name, email, password);

        // Llamar al método insertarUsuario de mi DAO
        int generatedId = userDAO.insertarUsuario(newUser);

        if (generatedId > 0) {
            request.setAttribute("mensaje", "¡Registro Exitoso! Usuario " + name + " fue registrado correctamente.");
            request.setAttribute("exito", true);
        } else {
            request.setAttribute("mensaje", "Error en el registro. El correo ya podría estar en uso.");
            request.setAttribute("exito", false);
        }

        // Reenviar al JSP de resultado
        request.getRequestDispatcher("resultado.jsp").forward(request, response);
    }
}
