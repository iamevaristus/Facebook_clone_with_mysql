package com.fb.facebook.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class PostService {
    public static boolean createPost(Connection connection, String post, String userId) {
        try {
            String query = "INSERT INTO posts(post_id, post, post_time, user_id) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, post);
            preparedStatement.setString(3, LocalDateTime.now().toString());
            preparedStatement.setString(4, userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println("Hey" + e.getMessage());
            return false;
        }
    }

    public static boolean createComment(Connection connection, String comment, String userId, String postId) {
        try {
            String query = "INSERT INTO comments(post_id, comment, comment_time, user_id, comment_id) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, postId);
            preparedStatement.setString(2, comment);
            preparedStatement.setString(3, LocalDateTime.now().toString());
            preparedStatement.setString(4, userId);
            preparedStatement.setString(5, UUID.randomUUID().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean editPost(Connection connection, String post, String postId, String userId) {
        try {
            String query = "UPDATE posts SET post = ?, post_time = ? WHERE post_id = ? AND user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, post);
            preparedStatement.setString(2, LocalDateTime.now().toString());
            preparedStatement.setString(3, postId);
            preparedStatement.setString(4, userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean editComment(Connection connection, String comment, String postId, String userId, String commentId) {
        try {
            String query = "UPDATE comments SET comment = ?, comment_time = ? WHERE post_id = ? AND comment_id = ? AND user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, comment);
            preparedStatement.setString(2, LocalDateTime.now().toString());
            preparedStatement.setString(3, postId);
            preparedStatement.setString(4, commentId);
            preparedStatement.setString(5, userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean likeOrUnlikePost(Connection connection, String userId, String postId, boolean likeState) {
        try {
            String query;
            if(likeState) {
                insertIntoDeleted(connection, userId,postId, "");
                query = "DELETE FROM post_likes WHERE post_id = ? AND user_id = ?;";
            } else {
                query = "INSERT INTO post_likes(post_id, user_id) VALUES(?,?) ";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, postId);
            preparedStatement.setString(2, userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean likeOrUnlikeComment(
            Connection connection, String userId, String postId, String commentId, boolean likeState
    ) {
        try {
            // "INSERT INTO likes(post_id, user_id, comment_id) VALUES(?,?,?) ON DUPLICATE KEY UPDATE post_id = post_id, user_id = user_id";
            String query;
            if(likeState) {
                query = "DELETE FROM comment_likes WHERE post_id = ? AND user_id = ? AND comment_id = ?";
            } else {
                query = "INSERT INTO comment_likes(post_id, user_id, comment_id) VALUES(?,?,?) ";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, postId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, commentId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean deletePost(Connection connection, String postId, String userId) {
        try {
            insertIntoDeleted(connection, userId,postId, "");
            String query = "DELETE FROM posts WHERE post_id = ? AND user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, postId);
            preparedStatement.setString(2, userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    protected static void insertIntoDeleted(Connection connection, String userId, String postId, String commentId) throws SQLException {
        String query;
        if(commentId.isEmpty()) {
            query = "INSERT INTO deleted(post_id, user_id) VALUES (?,?)";
        } else {
            query = "INSERT INTO deleted(post_id, user_id, comment_id) VALUES (?,?,?)";
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, postId);
        preparedStatement.setString(2, userId);
        if(!commentId.isEmpty()) {
            preparedStatement.setString(3, commentId);
        }
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static boolean deleteComment(Connection connection, String postId, String userId, String commentId) {
        try {
            insertIntoDeleted(connection, userId,postId, commentId);
            String query = "DELETE FROM comments WHERE post_id = ? AND user_id = ? AND comment_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, postId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, commentId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
