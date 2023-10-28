<%@ page import="com.fb.facebook.utils.Commons" %>
<%@ page import="com.fb.facebook.utils.Utils" %>
<%@ page import="com.fb.facebook.controllers.HomeController" %>
<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 10/25/23
  Time: 9:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<!-- Check if the 'posts' list is empty -->
<c:choose>
  <c:when test="${empty HomeController.posts or HomeController.posts.size() == 0}">
    <div style="padding: 225px 50px">
      <h2 style="font-size: 80px; text-align: center">🫠</h2>
      <p style="text-align: center">No posts yet, be the first!</p>
    </div>
  </c:when>
  <c:otherwise>
    <c:forEach items="${HomeController.posts}" var="post">
      <div class="friends_post">
        <div class="friend_post_top">
          <div class="img_and_name">
            <div style="
                background-color: #eeebeb;
                display: flex;
                justify-content: center; /* Horizontal centering */
                align-items: center; /* Vertical centering */
                width: 50px;
                height: 50px;
                border-radius: 50%;
                margin-right: 10px;
            ">
              <h4 style="font-size: 30px; text-align: center; margin: 0;">${post.postedByAvatar}</h4>
            </div>
            <div class="friends_name">
              <form action="home" method="post">
                <input type="hidden" value="${post.userId}" name="<%=Commons.viewProfile%>">
                <button class="friends_name" type="submit" style="background-color: transparent;
                  box-shadow: none;
                  font-size: 16px;
                  border: none;"
                  >${post.postedBy}</button>
              </form>
              <p class="time">${post.dateTime}.<i class="fa-solid fa-user-group"></i></p>
            </div>
          </div>
          <div class="menu">
            <i class="fa-solid fa-ellipsis"></i>
          </div>
        </div>
        <p style="font-size: 18px">${post.post}</p>
        <div class="info">
          <%-- Like Section --%>
          <c:choose>
            <c:when test="${post.numberOfLikes == 0}">
              <div class="emoji_img">
                <p>Be the first to like this post</p>
              </div>
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${post.likedByCurrentUser and post.numberOfLikes > 1}">
                  <div class="emoji_img">
                    <i class="fa-solid fa-thumbs-up" style="color: #0061ff;"></i>
                    <p style="margin-left: 10px;">You and ${post.numberOfLikes - 1} others like this post</p>
                  </div>
                </c:when>
                <c:otherwise>
                  <c:choose>
                    <c:when test="${post.likedByCurrentUser}">
                      <div class="emoji_img">
                        <i class="fa-solid fa-thumbs-up" style="color: #0061ff;"></i>
                        <p style="margin-left: 10px;">You liked this post</p>
                      </div>
                    </c:when>
                    <c:otherwise>
                      <div class="emoji_img">
                        <i class="fa-solid fa-thumbs-up" style="color: #0061ff;"></i>
                        <p style="margin-left: 10px;">${post.numberOfLikes} people like this post</p>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
          <%-- Comments Section --%>
          <c:choose>
            <c:when test="${post.numberOfComments == 0}">
              <p style="
                padding: 0 12px;
                font-size: 15px;
                color: #919191;">No comment</p>
            </c:when>
            <c:otherwise>
              <p style="
                padding: 0 12px;
                font-size: 15px;
                color: #919191;">${post.numberOfComments} comments</p>
            </c:otherwise>
          </c:choose>
        </div>
        <hr>
        <div class="like">
          <form action="home" method="post">
            <input type="hidden" name="<%=Commons.likePost%>" value="${post.postId} ${post.likedByCurrentUser}">
            <c:choose>
              <c:when test="${post.likedByCurrentUser}">
                <button type="submit" class="like_icon like-button">
                  <i class="fa-solid fa-thumbs-up"></i>
                  Like
                </button>
              </c:when>
              <c:otherwise>
                <button type="submit" class="like_icon">
                  <i class="fa-solid fa-thumbs-up"></i>
                  Like
                </button>
              </c:otherwise>
            </c:choose>
          </form>
          <div class="like_icon">
            <i class="fa-solid fa-message"></i>
            <p>Comments</p>
          </div>
          <div class="like_icon">
            <i class="fa-solid fa-share"></i>
            <p>Share</p>
          </div>
          <c:choose>
            <c:when test="${post.postedByCurrentUser}">
              <!--/// TODO:: --> <div class="like_icon edit-button open_edit_post"
                   data-post-content="${post.post} *****PostID${post.postId}"
              >
                <i class="fa-solid fa-marker"></i>
                <p>Edit Post</p>
              </div>
              <form action="home" method="post">
                <input type="hidden" name="<%=Commons.deletePost%>" value="${post.postId}">
                <button type="submit" class="like_icon delete-button">
                  <i class="fa-solid fa-trash"></i>
                  Delete Post
                </button>
              </form>
            </c:when>
          </c:choose>
        </div>
        <hr>
        <div class="comment_wrapper">
          <c:choose>
            <c:when test="${empty post.comments or post.comments.size() == 0}">
              <%-- Do nothing --%>
            </c:when>
            <c:otherwise>
              <c:forEach items="${post.comments}" var="comment">
                <div class="comment-row" style="margin: 14px 0; display: flex; align-items: start; justify-content: flex-start">
                  <div style="
                      background-color: #eeebeb;
                      display: flex;
                      justify-content: center; /* Horizontal centering */
                      align-items: center; /* Vertical centering */
                      width: 45px;
                      height: 45px;
                      border-radius: 50%;
                      margin-right: 20px;
                  ">
                    <h4 style="font-size: 25px; text-align: center; margin: 0;">${comment.commentedByAvatar}</h4>
                  </div>
                  <div class="comment-column" style=" width: 100%">
                    <div style="display: flex; align-items: center; justify-content: space-between;">
                      <form action="home" method="post">
                        <input type="hidden" value="${comment.userId}" name="<%=Commons.viewProfile%>">
                        <button class="friends_name" type="submit" style="background-color: transparent;
                  box-shadow: none;
                  font-size: 16px;
                  border: none;"
                        >${comment.commentedBy}</button>
                      </form>
<%--                      <p style="font-size: 16px;">${comment.commentedBy}</p>--%>
                      <p style="font-size: 14px; text-align: center; margin: 0; color: #9a9a9a">
                          ${comment.numberOfLikes} Likes
                      </p>
                    </div>
                    <div class="comment-buttons" style="display: flex; justify-content: space-between; width: 100%; align-items: center">
                      <p style="font-size: 16px; color: #555555">${comment.comment}</p>
                      <div style="display: flex; align-items: center">
                        <form action="home" method="post">
                          <input type="hidden" name="<%=Commons.likeComment%>" value="${comment.postId} ${comment.commentId} ${comment.likedByCurrentUser}">
                          <c:choose>
                            <c:when test="${comment.likedByCurrentUser}">
                              <button type="submit" class="like_icon like-button"><i class="fa-solid fa-thumbs-up"></i></button>
                            </c:when>
                            <c:otherwise>
                              <button type="submit" class="like_icon"><i class="fa-solid fa-thumbs-up"></i></button>
                            </c:otherwise>
                          </c:choose>
                        </form>
                        <c:choose>
                          <c:when test="${comment.commentedByCurrentUser}">
                            <!--/// TODO:: --> <div class="like_icon edit-button open_edit_comment"
                                 data-comment-content="${comment.comment} *****CommentID${comment.commentId} *****PostID${comment.postId}"
                            ><i class="fa-solid fa-marker"></i></div>
                            <form action="home" method="post">
                              <input type="hidden" name="<%=Commons.deleteComment%>" value="${comment.commentId} ${comment.postId}">
                              <button type="submit" class="like_icon delete-button"><i class="fa-solid fa-trash"></i></button>
                            </form>
                          </c:when>
                        </c:choose>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
          <form action="home" method="post" style="display: flex; flex: 1">
            <div style="
              background-color: #eeebeb;
              display: flex;
              justify-content: center; /* Horizontal centering */
              align-items: center; /* Vertical centering */
              width: 45px;
              height: 45px;
              border-radius: 50%;
              margin-right: 20px;
            ">
              <h4 style="font-size: 25px; text-align: center; margin: 0;"><%=Utils.getAvatarFromGender(HomeController.currentUser.getGender())%></h4>
            </div>
            <div class="comment_search">
              <label>
                <input type="hidden" value="${post.postId}" name="<%=Commons.createCommentPostId%>">
                <input type="text" placeholder="Write a comment" name="<%=Commons.createComment%>">
              </label>
            </div>
            <button type="submit" class="comment_icon send-post">
              <i class="fa-solid fa-paper-plane" style="margin-right: 8px;"></i>
              Comment
            </button>
          </form>
        </div>
      </div>
    </c:forEach>
  </c:otherwise>
</c:choose>