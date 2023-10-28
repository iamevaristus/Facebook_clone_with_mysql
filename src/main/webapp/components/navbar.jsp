<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.fb.facebook.controllers.HomeController" %>

<nav>
    <div class="left">
        <div class="logo">
            <a href=""${pageContext.request.contextPath}"">
                <img src="https://i.postimg.cc/NMyj90t9/logo.png" alt="Facebook">
            </a>
        </div>
        <div class="search_bar">
            <i class="fa-solid fa-magnifying-glass"></i>
            <label>
                <input type="search" placeholder="Search Facebook">
            </label>
        </div>
    </div>

<%--    <div class="center">--%>
<%--        <a href=""${pageContext.request.contextPath}""><i class="fa-solid fa-house"></i></a>--%>
<%--        <i class="fa-solid fa-tv"></i>--%>
<%--        <i class="fa-solid fa-store"></i>--%>
<%--        <i class="fa-solid fa-users"></i>--%>
<%--    </div>--%>

    <div class="right">
        <i class="fa-solid fa-list-ul"></i>
        <i class="fa-brands fa-facebook-messenger"></i>
        <i class="fa-solid fa-bell"></i>
        <i class="fa-solid fa-moon"></i>
        <form action="home" method="post">
            <input type="hidden" value="${HomeController.currentUser.userId}" name="<%=Commons.viewProfile%>">
            <button class="profile-header" type="submit" style="
                  box-shadow: none;
                  font-size: 12px;
                  border: none;color: white;
                  background-color: #166fe5;
                  padding: 10px;
                  border-radius: 50%;
                  margin-left: 10px;"
            ><%=String.valueOf(HomeController.currentUser.getFirstName().charAt(0))
                    + String.valueOf(HomeController.currentUser.getLastName().charAt(0))%></button>
        </form>
    </div>
</nav>