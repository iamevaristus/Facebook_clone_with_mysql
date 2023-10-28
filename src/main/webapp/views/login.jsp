<%@ page import="com.fb.facebook.utils.Commons" %><%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>
<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 10/24/23
  Time: 12:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<% Object errorParam = request.getAttribute(Commons.authError); %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <style>
            <%@include file="../css/login.css"%>
        </style>
        <title>Facebook - Login to your account</title>
        <link rel="icon" href="<c:url value="/resources/images/facebook.ico"/>" type="image/x-icon"/>
        <meta name="description" content="Login using email or username and password">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#006AFF">
    </head>
    <body>
        <div class="box">
            <div class="title-box">
                <img src="https://i.postimg.cc/NMyj90t9/logo.png" alt="Facebook">
                <p>Facebook helps you connect and share with the people in your life.</p>
            </div>
            <div class="form-box">
                <h3 style="
                            text-align: left; color: #166fe5;
                          ">Welcome back, </h3>
                <form id="login-form" action="login" method="post">
                    <% if (errorParam != null) { %>
                        <div style="
                            background-color: lightyellow;
                            padding: 10px;
                            border-radius: 15px;
                            margin-bottom: 30px;
                        ">
                            <p style="
                                color: indianred;
                                text-align: left;
                                font-size: 14px;
                                margin-block-start: 0;
                                margin-block-end: 0;
                            ">Incorrect username or password. Please try again.</p>
                        </div>
                    <% } %>
                    <label>
                        <input type="text" placeholder="Email address or username" name="<%=Commons.loginEmailOrUsername%>" required>
                    </label>
                    <label>
                        <input type="password" placeholder="Password" name="<%=Commons.loginPassword%>" required>
                    </label>
                    <button type="submit">Log In</button>
                </form>
                <hr>
                <p style="
                            color: #555555;
                            font-size: 12px;
                          ">Don't have an account?</p>
                <div class="create-btn">
                    <a href="${pageContext.request.contextPath}/signup" rel="noopener">Create New Account</a>
                </div>
            </div>
            <div id="loading-popup">
                <div id="loading-content">
                    <iframe src="https://lottie.host/?file=5101693a-277c-4e40-89f7-13e58c446517/efgLTY4uvf.lottie"></iframe>
                </div>
            </div>
        </div>
        <script>
            // Function to show the loading popup
            function showLoadingPopup() {
                document.getElementById("loading-popup").style.display = "block";
            }

            // Function to hide the loading popup
            function hideLoadingPopup() {
                document.getElementById("loading-popup").style.display = "none";
            }

            // Add an event listener to the login form
            document.getElementById("login-form").addEventListener("submit", function () {
                // Show the loading popup when the form is submitted
                showLoadingPopup();
            });
        </script>
    </body>
</html>

