package com.fb.facebook.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class Comment {
    private String comment;
    private String dateTime;
    private UUID userId;
    private UUID postId;
    private boolean isEdited;
    private String commentId;
    private boolean isLikedByCurrentUser;
    private boolean isCommentedByCurrentUser;
    private int numberOfLikes;
    private String commentedBy;
    private String commentedByAvatar;
}
