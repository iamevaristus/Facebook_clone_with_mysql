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
        name="Signup",
        value = "/signup",
        description = "Login to your facebook account using your unique details",
        smallIcon = "../../../resources/images/facebook (4).png"
)
public class SignupController extends HttpServlet {
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
        String firstname = request.getParameter(Commons.signupFirstName);
        String lastname = request.getParameter(Commons.signupLastName);
        String emailAddress = request.getParameter(Commons.signupEmailAddress);
        String username = request.getParameter(Commons.signupUsername);
        String password = request.getParameter(Commons.signupPassword);
        String gender = request.getParameter(Commons.signupGender);

        if(firstname != null && lastname != null && emailAddress != null && username != null && password != null) {
            if(!emailAddress.contains("@")) {
                request.setAttribute(Commons.authError, Commons.invalidEmailError);
                loadView(request, response);
            } else if(password.length() < 5) {
                request.setAttribute(Commons.authError, Commons.invalidPassword);
                loadView(request, response);
            } else if(AuthService.checkIfUsernameAlreadyExists(dbConnection.getConnection(), username)) {
                request.setAttribute(Commons.authError, Commons.usernameExistsError);
                loadView(request, response);
            } else if(AuthService.checkIfEmailAlreadyExists(dbConnection.getConnection(), emailAddress)) {
                request.setAttribute(Commons.authError, Commons.emailExistsError);
                loadView(request, response);
            } else {
                int result = AuthService.signup(
                        dbConnection.getConnection(),
                        firstname,
                        lastname,
                        emailAddress,
                        username,
                        password,
                        gender,
                        request
                );

                if(result == 200) {
                    response.sendRedirect("/");
                } else {
                    request.setAttribute(Commons.authError, Commons.signupError);
                    loadView(request, response);
                }
            }
        } else {
            request.setAttribute(Commons.authError, Commons.invalidDetails);
            loadView(request, response);
        }
    }

    @SneakyThrows
    private void loadView(HttpServletRequest request, HttpServletResponse response) {
        request.getServletContext().getRequestDispatcher("/views/signup.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        dbConnection.closeConnection();
    }
}
