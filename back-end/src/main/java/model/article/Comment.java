/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.article;

import java.sql.Timestamp;

/**
 *
 * @author Hanh
 */
public class Comment {
    private int commentId, likes, dislikes, articleId, userId;
    private String commentContent;
    private Timestamp commentTime;

    public Comment(int commentId, int likes, int dislikes, String commentContent, 
            Timestamp commentTime, int articleId, int userId) {
        this.commentId = commentId;
        this.likes = likes;
        this.dislikes = dislikes;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.articleId = articleId;
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public String toString() {
        return "Comment{" + "commentId=" + commentId + ", likes=" + likes + ", dislikes=" + dislikes + ", articleId=" + articleId + ", userId=" + userId + ", commentContent=" + commentContent + ", commentTime=" + commentTime + '}';
    }
}