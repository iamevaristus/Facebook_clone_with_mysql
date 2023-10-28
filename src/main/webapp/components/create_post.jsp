<%@ page import="com.fb.facebook.utils.Commons" %>
<%@ page import="com.fb.facebook.controllers.HomeController" %><%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 10/25/23
  Time: 9:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="create_post" class="modal">
    <div class="modal-content">
        <h3 style="color: #134892;"><%=HomeController.currentUser.getFirstName()%>, start creating your fun post 🖊</h3>
        <% if (HomeController.errorTag != null && HomeController.errorTag.equals(Commons.noContentInPostError)) { %>
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
                ">Post content cannot be empty. Share that fun beat in your heart!</p>
            </div>
        <% } %>
        <label class="input-container">
            <textarea
                    id="createPostInput"
                    name="<%=Commons.createPost%>"
                    form="createPostForm"
                    placeholder="">
            </textarea>
        </label>
        <form action="home" method="post" name="createPostForm" id="createPostForm" enctype="application/x-www-form-urlencoded">
            <div class="post-buttons">
                <div class="post_icon cancel-post" id="close_create_post">
                    <i class="fa-solid fa-xmark" style="margin-right: 8px;"></i>
                    <p>Cancel</p>
                </div>
                <button type="submit" class="post_icon send-post">
                    <i class="fa-solid fa-paper-plane" style="margin-right: 8px;"></i>
                    Send Post
                </button>
            </div>
        </form>
    </div>
</div>
