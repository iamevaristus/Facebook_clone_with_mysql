package com.fb.facebook.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class Post {
    private String post;
    private String postedBy;
    private String postedByAvatar;
    private UUID postId;
    private UUID userId;
    private String dateTime;
    private int numberOfComments;
    private int numberOfLikes;
    private boolean isLikedByCurrentUser;
    private boolean isCommentedByCurrentUser;
    private boolean isPostedByCurrentUser;
    private List<Comment> comments;
}
