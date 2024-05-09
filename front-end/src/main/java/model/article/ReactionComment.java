/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.article;

import model.user.User;

/**
 *
 * @author Hanh
 */
public class ReactionComment {
    private int reactionArticleId, userId, CommentId;
    private boolean reationType;

    public ReactionComment(int reactionArticleId, boolean reationType, int userId, int CommentId) {
        this.reactionArticleId = reactionArticleId;
        this.reationType = reationType;
        this.userId = userId;
        this.CommentId = CommentId;
    }

    public int getReactionArticleId() {
        return reactionArticleId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCommentId() {
        return CommentId;
    }

    public boolean isReationType() {
        return reationType;
    }

    public void setReationType(boolean reationType) {
        this.reationType = reationType;
    }

    @Override
    public String toString() {
        return "ReactionComment{" + "reactionArticleId=" + reactionArticleId + ", userId=" + userId + ", CommentId=" + CommentId + ", reationType=" + reationType + '}';
    }
}