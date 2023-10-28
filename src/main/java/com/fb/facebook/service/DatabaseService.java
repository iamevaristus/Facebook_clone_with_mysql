package com.fb.facebook.service;

import com.fb.facebook.models.Comment;
import com.fb.facebook.models.Post;
import com.fb.facebook.models.User;
import com.fb.facebook.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseService {
    public static List<Post> getPosts(Connection connection) throws SQLException {
        String query = "SELECT * FROM posts";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<Post> posts = new ArrayList<>();
        while (resultSet.next()) {
            Post post = Post.builder()
                    .userId(UUID.fromString(resultSet.getString("user_id")))
                    .postId(UUID.fromString(resultSet.getString("post_id")))
                    .post(resultSet.getString("post"))
                    .dateTime(resultSet.getString("post_time"))
                    .numberOfLikes(resultSet.getInt("total_number_of_likes"))
                    .numberOfComments(resultSet.getInt("total_number_of_comments"))
                    .build();

            post.setPostedByAvatar(Utils.getAvatarFromGender(getUserBasedOnUserId(connection, post.getUserId()).getGender()));
            post.setPostedBy(getUserBasedOnUserId(connection, post.getUserId()).fullName());
            post.setComments(getCommentsBasedOnPostId(connection, post.getPostId()));
            posts.add(post);
        }
        return posts;
    }

    public static User getUserBasedOnUserId(Connection connection, UUID userId) throws SQLException {
        String userQuery = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement userStatement = connection.prepareStatement(userQuery);
        userStatement.setString(1, userId.toString());
        ResultSet userSet = userStatement.executeQuery();
        User user = User.builder().build();
        while (userSet.next()) {
            user.setUserId(UUID.fromString(userSet.getString("user_id")));
            user.setPassword(userSet.getString("password"));
            user.setUsername(userSet.getString("user_name"));
            user.setLastName(userSet.getString("last_name"));
            user.setFirstName(userSet.getString("first_name"));
            user.setEmailAddress(userSet.getString("email_address"));
            user.setTotalNumberOfPosts(userSet.getInt("total_number_of_posts"));
            user.setGender(Utils.getGenderFromString(userSet.getString("gender")));
        }
        return user;
    }

    public static List<Comment> getCommentsBasedOnPostId(Connection connection, UUID postId) throws SQLException {
        String commentQuery = "SELECT * FROM comments WHERE post_id = ?";
        PreparedStatement commentStatement = connection.prepareStatement(commentQuery);
        commentStatement.setString(1, postId.toString());
        ResultSet commentSet = commentStatement.executeQuery();
        List<Comment> comments = new ArrayList<>();

        while(commentSet.next()) {
            Comment comment = Comment.builder()
                    .commentId(commentSet.getString("comment_id"))
                    .comment(commentSet.getString("comment"))
                    .isEdited(commentSet.getInt("is_edited") != 0)
                    .userId(UUID.fromString(commentSet.getString("user_id")))
                    .postId(UUID.fromString(commentSet.getString("post_id")))
                    .dateTime(commentSet.getString("comment_time"))
                    .build();

            comment.setCommentedByAvatar(Utils.getAvatarFromGender(
                    getUserBasedOnUserId(connection, comment.getUserId()).getGender()
            ));
            comment.setCommentedBy(getUserBasedOnUserId(connection, comment.getUserId()).fullName());
            comments.add(comment);
        }
        return comments;
    }

    public static boolean isPostLikedByCurrentUser(UUID userId, UUID postId, Connection connection) throws SQLException {
        String likeQuery = "SELECT * FROM post_likes WHERE post_id = ? AND user_id = ?";
        PreparedStatement likeStatement = connection.prepareStatement(likeQuery);
        likeStatement.setString(1, postId.toString());
        likeStatement.setString(2, userId.toString());
        ResultSet likeSet = likeStatement.executeQuery();
        return likeSet.next();
    }

    public static boolean isCommentLikedByCurrentUser(UUID userId, UUID postId, UUID commentId, Connection connection) throws SQLException {
        String likeQuery = "SELECT * FROM comment_likes WHERE post_id = ? AND user_id = ? AND comment_id = ?";
        PreparedStatement likeStatement = connection.prepareStatement(likeQuery);
        likeStatement.setString(1, postId.toString());
        likeStatement.setString(2, userId.toString());
        likeStatement.setString(3, commentId.toString());
        ResultSet likeSet = likeStatement.executeQuery();
        return likeSet.next();
    }

    public static int numberOfCommentLikes(UUID commentId, Connection connection) throws SQLException {
        AtomicInteger integer = new AtomicInteger();
        String likeQuery = "SELECT * FROM comment_likes WHERE comment_id = ?";
        PreparedStatement likeStatement = connection.prepareStatement(likeQuery);
        likeStatement.setString(1, commentId.toString());
        ResultSet likeSet = likeStatement.executeQuery();
        while(likeSet.next()) {
            integer.getAndSet(integer.get() + 1);
        }
        likeSet.close();
        return integer.get();
    }
}
