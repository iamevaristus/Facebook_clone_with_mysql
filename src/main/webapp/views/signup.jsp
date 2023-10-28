<%@ page import="com.fb.facebook.utils.Commons" %><%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 10/24/23
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<% Object errorParam = request.getAttribute(Commons.authError); %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <style>
            <%@include file="../css/signup.css"%>
        </style>
        <title>Facebook - Create your account</title>
        <link rel="icon" href="<c:url value="/resources/images/facebook.ico"/>" type="image/x-icon"/>
        <meta name="description" content="Signup with username, email and a password.">
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
            ">Signup today</h3>
                <p style="
              color: #5a5959; font-size: 12px; text-align: left;
            ">Choose a unique username and start connecting</p>
                <form id="signup-form" action="signup" method="post">
                    <%
                        String uiResponse;
                        if (errorParam != null) {
                            if(errorParam.equals(Commons.emailExistsError)) {
                                uiResponse = Commons.emailExistsError;
                            } else if(errorParam.equals(Commons.usernameExistsError)) {
                                uiResponse = Commons.usernameExistsError;
                            } else if(errorParam.equals(Commons.invalidEmailError)) {
                                uiResponse = Commons.invalidEmailError;
                            } else if(errorParam.equals(Commons.invalidPassword)) {
                                uiResponse = Commons.invalidPassword;
                            } else if(errorParam.equals(Commons.invalidDetails)) {
                                uiResponse = Commons.invalidDetails;
                            } else {
                                uiResponse = Commons.signupError;
                            }
                    %>
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
                                "><%= uiResponse %></p>
                        </div>
                    <% } %>
                    <div style="display: flex; align-items: center; justify-content: space-between">
                        <label title="First Name" style="padding-right: 10px">
                            <input type="text" placeholder="First name" required name="<%=Commons.signupFirstName%>">
                        </label>
                        <label>
                            <input type="text" placeholder="Last name" required name="<%=Commons.signupLastName%>">
                        </label>
                    </div>
                    <label>
                        <input type="email" placeholder="Email Address" required name="<%=Commons.signupEmailAddress%>">
                    </label>
                    <label>
                        <input type="text" placeholder="Username" required name="<%=Commons.signupUsername%>">
                    </label>
                    <label>
                        <input type="text" placeholder="Gender (Male, Female, Prefer Not to Say, Others)" required name="<%=Commons.signupGender%>">
                    </label>
                    <label>
                        <input type="password" placeholder="New Password" required name="<%=Commons.signupPassword%>">
                    </label>
                    <p class="click_to_agree">By clicking create account, you agree to our terms, data policy and cookies policy.
                    </p>
                    <button type="submit" value="Sign Up">Create account</button>
                </form>
                <hr>
                <div class="create-btn">
                    <a href="${pageContext.request.contextPath}/login" rel="noopener">Already have an account? Click here</a>
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
            document.getElementById("signup-form").addEventListener("submit", function () {
                // Show the loading popup when the form is submitted
                showLoadingPopup();
            });
        </script>
    </body>
</html>