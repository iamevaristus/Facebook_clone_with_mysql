package com.fb.facebook.service;

import com.fb.facebook.models.User;
import com.fb.facebook.utils.Commons;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class AuthService {
    public static int login(Connection connection, String emailOrUsername, String password, HttpServletRequest request) {
        try {
            String query;
            if(!emailOrUsername.contains("@")) {
                query = "SELECT * FROM users WHERE user_name = ? AND password = ?";
            } else {
                query = "SELECT * FROM users WHERE email_address = ? AND password = ?";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, emailOrUsername);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                setSession(request, DatabaseService.getUserBasedOnUserId(
                        connection, UUID.fromString(resultSet.getString("user_id"))
                ));
                preparedStatement.close();
                resultSet.close();
                preparedStatement.close();
                return 200;
            } else {
                preparedStatement.close();
                resultSet.close();
                return 100;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 100;
        }
    }

    public static int signup(
            Connection connection,
            String firstname,
            String lastname,
            String email,
            String username,
            String password,
            String gender,
            HttpServletRequest request
    ) {
        try {
            UUID userId = UUID.randomUUID();
            String userQuery = "INSERT INTO users(" +
                    "first_name, last_name, email_address, user_name, user_id, password, gender" +
                    ") VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(userQuery);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, userId.toString());
            preparedStatement.setString(6, password);
            preparedStatement.setString(7, gender);
            preparedStatement.executeUpdate();
            setSession(request, User.builder()
                    .username(username).userId(userId).firstName(firstname)
                    .totalNumberOfPosts(0).lastName(lastname).emailAddress(email).build()
            );
            preparedStatement.close();
            return 200;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 100;
        }
    }

    public static boolean checkIfUsernameAlreadyExists(Connection connection, String username) {
        try {
            String usernameQuery = "SELECT user_name FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(usernameQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> usernames = new ArrayList<>();
            while(resultSet.next()) {
                usernames.add(resultSet.getString("user_name"));
            }
            preparedStatement.close();
            resultSet.close();
            AtomicBoolean result = new AtomicBoolean();
            usernames.forEach(user -> result.set(user.equalsIgnoreCase(username)));
            return result.get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static boolean checkIfEmailAlreadyExists(Connection connection, String emailAddress) {
        try {
            String emailAddressQuery = "SELECT email_address FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(emailAddressQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> usernames = new ArrayList<>();
            while(resultSet.next()) {
                usernames.add(resultSet.getString("email_address"));
            }
            preparedStatement.close();
            resultSet.close();
            AtomicBoolean result = new AtomicBoolean();
            usernames.forEach(user -> result.set(user.equalsIgnoreCase(emailAddress)));
            return result.get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static boolean verifySession(HttpServletRequest request) {
        if(request.getSession().getAttribute(Commons.currentUser) == null) {
            try {
                return false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        } else {
            return true;
        }
    }

    public static void setSession(HttpServletRequest request, User user) {
        request.getSession().setAttribute(Commons.currentUser, user);
    }
}
