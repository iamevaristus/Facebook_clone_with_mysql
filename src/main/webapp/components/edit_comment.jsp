<%@ page import="com.fb.facebook.utils.Commons" %>
<%@ page import="com.fb.facebook.controllers.HomeController" %><%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 10/25/23
  Time: 9:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal edit_comment">
    <div class="modal-content">
        <h3 style="color: #134892;">Hey <%=HomeController.currentUser.getFirstName()%>, you can now edit your comment 🖊</h3>
        <% if (HomeController.errorTag != null && HomeController.errorTag.equals(Commons.noChangesInPostContentError)) { %>
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
        ">Comment content has no changes to commit. Cancel or update the beatings of your heart content!</p>
        </div>
        <% } %>
        <label class="input-container">
      <textarea
              class="editCommentInput"
              name="<%=Commons.editComment%>"
              form="editCommentForm"
              placeholder="">
      </textarea>
        </label>
        <form action="home" method="post" name="editCommentForm" id="editCommentForm">
            <input type="hidden" name="<%=Commons.editCommentId%>" id="editCommentInputId">
            <input type="hidden" name="<%=Commons.editPostCommentId%>" class="editCommentPostInputId">
            <div class="post-buttons">
                <div class="post_icon cancel-post close_edit_comment">
                    <i class="fa-solid fa-xmark" style="margin-right: 8px;"></i>
                    <p>Cancel Update</p>
                </div>
                <button type="submit" class="post_icon send-post">
                    <i class="fa-solid fa-paper-plane" style="margin-right: 8px;"></i>
                    Update Comment
                </button>
            </div>
        </form>
    </div>
</div>
