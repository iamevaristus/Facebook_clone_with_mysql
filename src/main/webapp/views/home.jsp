<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 10/24/23
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<c:if test="${not empty HomeController.currentUser or HomeController.currentUser != null}">
    <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Facebook - Home</title>
            <link rel="icon" href="<c:url value="/resources/images/facebook.ico"/>" type="image/x-icon"/>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css" integrity="sha512-1sCRPdkRXhBV2PBLUdRb4tMg1w2YPf37qatUFeS7zlBy7jJI8Lf4VHwWfZZfpXtYSLy85pkm9GaYVYMfw5BC1A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
            <style>
                <%@include file="../css/home.css"%>
                <%@include file="../css/create_post.css"%>
                <%@include file="../css/main_left.css"%>
                <%@include file="../css/main_right.css"%>
            </style>
        </head>
        <body>
        <%@include file="../components/navbar.jsp"%>
        <!------------main--------------->
        <div class="main">
            <!------------major---------------------->
            <div class="left">
                <div class="my_post">
                    <form action="home" method="post">
                        <div class="post_top">
                            <div style="background-color: #eeebeb; border-radius: 10px; padding: 7px 12px">
                                <h2>👤</h2>
                            </div>
                            <div class="input-container" id="open_create_post">
                                <p style="color: #cccccc"><%= "What's on your mind, " + HomeController.currentUser.getFirstName() + "?" %></p>
                            </div>
                        </div>
                        <hr>
                        <div class="post_bottom">
                            <div class="post_icon red">
                                <i class="fa-solid fa-video" style="color: #ff2600; margin-right: 8px; font-size: 23px;"></i>
                                <p>Live Video</p>
                            </div>
                            <div class="post_icon">
                                <i class="fa-solid fa-images green"></i>
                                <p>Photo/video</p>
                            </div>
                            <div class="post_icon">
                                <i class="fa-regular fa-face-grin yellow"></i>
                                <p>Feeling/activity</p>
                            </div>
                        </div>
                    </form>
                </div>
                <%@include file="../components/posts.jsp"%>
            </div>
            <!------------------right------------------>
            <div class="right">
                <div style="
            background-color: #134892;
            display: flex;
            justify-content: center; /* Horizontal centering */
            align-items: center; /* Vertical centering */
            width: 130px;
            height: 130px;
            border-radius: 50%;
            margin: 15px 40%;
        ">
                    <h4 style="font-size: 100px; text-align: center; margin: 0;"><%=Utils.getAvatarFromGender(HomeController.profile.getGender())%></h4>
                </div>
                <h2 style="
            font-size: 16px;
            color: #919191;
            text-align: center;
        "><%=HomeController.profile.fullName()%></h2>
                <h2 style="
            font-size: 16px;
            color: #919191;
            text-align: center;
        ">Total posts made: <%=HomeController.profile.getTotalNumberOfPosts()%></h2>
                <hr>
                <div class="second_wrapper">
                    <div class="contact_tag">
                        <h2>🖊 Posts from <%= HomeController.profile.getFirstName()%></h2>
                        <div class="contact_icon">
                            <i class="fa-solid fa-video"></i>
                            <i class="fa-solid fa-magnifying-glass"></i>
                            <i class="fa-solid fa-ellipsis"></i>
                        </div>
                    </div>

                        <%-- Posts from the user being viewed --%>
                    <%@include file="../components/display_profile_posts.jsp"%>
                </div>
            </div>
        </div>

            <%--    Form for creating posts --%>
        <%@include file="../components/create_post.jsp"%>
        <%@include file="../components/edit_post.jsp"%>
        <%@include file="../components/edit_comment.jsp"%>

        <script>
            // Get the modal and the button to open it
            const modal = document.getElementById("create_post");
            const openModalButton = document.getElementById("open_create_post");
            const closeModalButton = document.getElementById("close_create_post");
            const postInput = document.getElementById("postInput");

            // Open the modal when "Send Post" div is clicked
            openModalButton.onclick = function() {
                modal.style.display = "block";
                postInput.value.trim();
            }

            // Close the modal when the close button is clicked
            closeModalButton.onclick = function() {
                modal.style.display = "none";
                // Clear the textarea
                postInput.value = " ";
            }

            const editModal = document.querySelector(".edit_post");
            const openEdit = document.querySelector(".open_edit_post");
            const closeEdit = document.querySelector(".close_edit_post");
            const editInput = document.querySelector(".editPostInput");
            const editPostId = document.querySelector(".editPostInputId");

            // Open the editModal when "Send Post" div is clicked
            openEdit.onclick = function() {
                editModal.style.display = "block";
                editInput.value.trim();

                editInput.value = openEdit.getAttribute("data-post-content").split("*****PostID")[0];
                editPostId.value = openEdit.getAttribute("data-post-content").split("*****PostID")[1];
            }

            // Close the editModal when the close button is clicked
            closeEdit.onclick = function() {
                editModal.style.display = "none";
                // Clear the textarea
                editInput.value = " ";
            }

            const editCommentModal = document.querySelector(".edit_comment");
            const openEditComment = document.querySelector(".open_edit_comment");
            const closeEditComment = document.querySelector(".close_edit_comment");
            const editInputComment = document.querySelector(".editCommentInput");
            const editCommentId = document.querySelector(".editCommentInputId");
            const editCommentPostId = document.querySelector(".editCommentPostInputId");

            // Open the editModal when "Send Post" div is clicked
            openEditComment.onclick = function() {
                editCommentModal.style.display = "block";
                editInputComment.value.trim();

                const array = openEditComment.getAttribute("data-comment-content").split("*****CommentID");
                const newArray = array[1].split("*****PostID");

                editInputComment.value = array[0];
                editCommentId.value = newArray[0];
                editCommentPostId.value = newArray[1];
            }

            // Close the editCommentModal when the close button is clicked
            closeEditComment.onclick = function() {
                editCommentModal.style.display = "none";
                // Clear the textarea
                editInputComment.value = " ";
            }

            const openProfileEdit = document.getElementById("open_edit_post_profile");

            // Open the editModal when "Send Post" div is clicked
            openProfileEdit.onclick = function() {
                editModal.style.display = "block";

                editInput.value = openProfileEdit.getAttribute("data-post-content").split("*****PostID")[0];
                editPostId.value = openProfileEdit.getAttribute("data-post-content").split("*****PostID")[1];
            }

            // Close the editModal when the close button is clicked
            closeEdit.onclick = function() {
                editModal.style.display = "none";
                // Clear the textarea
                editInput.value = "";
            }

            const openEditProfileComment = document.getElementById("open_edit_comment_profile");

            // Open the editModal when "Send Post" div is clicked
            openEditProfileComment.onclick = function() {
                editCommentModal.style.display = "block";

                const array = openEditProfileComment.getAttribute("data-comment-content").split("*****CommentID");
                const newArray = array[1].split("*****PostID");

                editInputComment.value = array[0];
                editCommentId.value = newArray[0];
                editCommentPostId.value = newArray[1];
            }

            // Close the editCommentModal when the close button is clicked
            closeEditComment.onclick = function() {
                editCommentModal.style.display = "none";
                // Clear the textarea
                editInputComment.value = "";
            }
        </script>
        </body>
    </html>
</c:if>