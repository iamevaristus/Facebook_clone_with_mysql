package com.fb.facebook.controllers;

import com.fb.facebook.connection.DBConnection;
import com.fb.facebook.models.Comment;
import com.fb.facebook.models.Post;
import com.fb.facebook.models.User;
import com.fb.facebook.service.AuthService;
import com.fb.facebook.service.DatabaseService;
import com.fb.facebook.service.PostService;
import com.fb.facebook.utils.Commons;
import com.fb.facebook.utils.Utils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(
        name="Home",
        urlPatterns = {
                "/",
                "/home"
        },
        description = "Home for the people you love",
        smallIcon = "../../../resources/images/facebook (4).png"
)
public class HomeController extends HttpServlet {
    DBConnection dbConnection;
    public static User profile;
    public static User currentUser;
    public static List<Post> posts = new ArrayList<>();
    public static List<Post> profilePosts = new ArrayList<>();
    public static String errorTag;
    public static String post = "";

    public void init() {
        dbConnection = new DBConnection();
        dbConnection.initializeConnection();
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  // HTTP 1.1.
//        response.setHeader("Pragma", "no-cache");  // HTTP 1.0.
//        response.setDateHeader("Expires", 0);  // Proxies.

        currentUser = (User) request.getSession().getAttribute(Commons.currentUser);

        if(currentUser != null) {
            currentUser = DatabaseService.getUserBasedOnUserId(dbConnection.getConnection(), currentUser.getUserId());
            AuthService.setSession(request, currentUser);

            List<Post> fetchedPosts = DatabaseService.getPosts(dbConnection.getConnection());
            fetchedPosts.sort((a, b) -> LocalDateTime.parse(b.getDateTime())
                    .compareTo(LocalDateTime.parse(a.getDateTime())));
            for(Post post : fetchedPosts) {
                post.setLikedByCurrentUser(DatabaseService.isPostLikedByCurrentUser(
                        currentUser.getUserId(), post.getPostId(), dbConnection.getConnection())
                );
                post.setDateTime(Utils.formatDateTime(post.getDateTime()));
                post.setPostedByCurrentUser(post.getUserId().equals(currentUser.getUserId()));

                for(Comment comment : post.getComments()) {
                    comment.setLikedByCurrentUser(DatabaseService.isCommentLikedByCurrentUser(
                            currentUser.getUserId(), post.getPostId(),
                            UUID.fromString(comment.getCommentId()), dbConnection.getConnection())
                    );
                    comment.setNumberOfLikes(DatabaseService.numberOfCommentLikes(
                            UUID.fromString(comment.getCommentId()), dbConnection.getConnection())
                    );
                    comment.setDateTime(Utils.formatDateTime(comment.getDateTime()));
                    comment.setCommentedByCurrentUser(comment.getUserId().equals(currentUser.getUserId()));
                    post.setCommentedByCurrentUser(comment.getUserId().equals(currentUser.getUserId()));
                }
            }
            posts = fetchedPosts;
        }

        if(request.getSession().getAttribute(Commons.viewProfile) != null) {
            profile = (User) request.getSession().getAttribute(Commons.viewProfile);
        } else {
            profile = currentUser;
        }

        // Posts from the viewing profile = Default is set to posts made by the current user.
        if(profile != null) {
            profilePosts.clear();
            for(Post post1 : posts) {
                if(post1.getUserId().equals(profile.getUserId())) {
                    profilePosts.add(post1);
                }
            }
        }
        loadView(request, response, true);
    }

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if(request.getParameter(Commons.createPost) != null) {
            String post = request.getParameter(Commons.createPost);
            if(post.isEmpty()) {
                loadView(request, response, false);
            } else {
                if(PostService.createPost(dbConnection.getConnection(), post, currentUser.getUserId().toString())) {
                    loadView(request, response, false);
                } else {
                    /// TODO: Not uploaded
                    loadView(request, response, false);
                }
            }
        }

        if(request.getParameter(Commons.deletePost) != null) {
            if(PostService.deletePost(
                    dbConnection.getConnection(), request.getParameter(Commons.deletePost),
                    currentUser.getUserId().toString())
            ) {
                loadView(request, response, false);
            } else {
                /// TODO: Not uploaded
                loadView(request, response, false);
            }
        }

        if(request.getParameter(Commons.likePost) != null) {
            String likePost = request.getParameter(Commons.likePost);
            String postId = likePost.split(" ")[0];
            boolean likeState = Boolean.parseBoolean(likePost.split(" ")[1]);
            if(PostService.likeOrUnlikePost(dbConnection.getConnection(), currentUser.getUserId().toString(), postId, likeState)) {
                loadView(request, response, false);
            } else {
                /// TODO: Not uploaded
                loadView(request, response, false);
            }
        }

        if(request.getParameter(Commons.editPost) != null) {
            String post = request.getParameter(Commons.editPost);
            String postId = request.getParameter(Commons.editPostId);
            if(post.isEmpty()) {
                loadView(request, response, false);
            } else {
                if(PostService.editPost(
                        dbConnection.getConnection(), post, postId,
                        currentUser.getUserId().toString())
                ) {
                    loadView(request, response, false);
                } else {
                    /// TODO: Not uploaded
                    loadView(request, response, false);
                }
            }
        }

        if(request.getParameter(Commons.createComment) != null && request.getParameter(Commons.createCommentPostId) != null) {
            String comment = request.getParameter(Commons.createComment);
            String commentPostId = request.getParameter(Commons.createCommentPostId);
            if(PostService.createComment(dbConnection.getConnection(), comment, currentUser.getUserId().toString(), commentPostId)) {
                loadView(request, response, false);
            } else {
                /// TODO: Notify the user
                loadView(request, response, false);
            }
        }

        if(request.getParameter(Commons.editComment) != null) {
            String newComment = request.getParameter(Commons.editComment);
            String commentId = request.getParameter(Commons.editCommentId);
            String postId = request.getParameter(Commons.editPostCommentId);
            if(PostService.editComment(
                    dbConnection.getConnection(), newComment, postId,
                    currentUser.getUserId().toString(), commentId)
            ) {
                loadView(request, response, false);
            } else {
                loadView(request, response, false);
            }
        }

        if(request.getParameter(Commons.deleteComment) != null) {
            String post = request.getParameter(Commons.deleteComment);
            if(PostService.deleteComment(
                    dbConnection.getConnection(), post.split(" ")[1],
                    currentUser.getUserId().toString(), post.split(" ")[0]
            )) {
                loadView(request, response, false);
            } else {
                loadView(request, response, false);
            }
        }

        if(request.getParameter(Commons.likeComment) != null) {
            String like = request.getParameter(Commons.likeComment);
            if(PostService.likeOrUnlikeComment(
                    dbConnection.getConnection(), currentUser.getUserId().toString(),
                    like.split(" ")[0], like.split(" ")[1],
                    Boolean.parseBoolean(like.split(" ")[2])
            )) {
                loadView(request, response, false);
            } else {
                loadView(request, response, false);
            }
        }

        if(request.getParameter(Commons.viewProfile) != null) {
            String profileId = request.getParameter(Commons.viewProfile);
            User viewProfile = DatabaseService.getUserBasedOnUserId(dbConnection.getConnection(), UUID.fromString(profileId));
            request.getSession().setAttribute(Commons.viewProfile, viewProfile);
            loadView(request, response, false);
        }
    }

    @SneakyThrows
    private void loadView(HttpServletRequest request, HttpServletResponse response, boolean shouldForward) {
        if(shouldForward) {
            getServletContext().getRequestDispatcher("/views/home.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        dbConnection.closeConnection();
    }
}
