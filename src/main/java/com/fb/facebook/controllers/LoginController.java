package com.fb.facebook.controllers;

import com.fb.facebook.connection.DBConnection;
import com.fb.facebook.service.AuthService;
import com.fb.facebook.utils.Commons;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

@WebServlet(
        name="Login",
        value = "/login",
        description = "Login to your facebook account using your unique details",
        smallIcon = "../../../resources/images/facebook (4).png"
)
public class LoginController extends HttpServlet {
    DBConnection dbConnection;

    public void init() {
        dbConnection = new DBConnection();
        dbConnection.initializeConnection();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        loadView(request, response);
    }

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String emailOrUsername = request.getParameter(Commons.loginEmailOrUsername);
        String password = request.getParameter(Commons.loginPassword);

        if(emailOrUsername != null && password != null) {
            request.getSession().invalidate();
            int result = AuthService.login(dbConnection.getConnection(), emailOrUsername, password, request);
            if(result == 100) {
                request.setAttribute(Commons.authError, true);
                loadView(request, response);
            } else {
                response.sendRedirect("/");
            }
        } else {
            request.setAttribute(Commons.authError, true);
            loadView(request, response);
        }
    }

    @SneakyThrows
    private void loadView(HttpServletRequest request, HttpServletResponse response) {
        request.getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        dbConnection.closeConnection();
    }
}
