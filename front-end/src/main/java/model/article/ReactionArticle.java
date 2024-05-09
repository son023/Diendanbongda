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
public class ReactionArticle {
    private int reactionArticleId, articleId, userId;
    private boolean reationType;

    public ReactionArticle(int reactionArticleId, boolean reationType, int userId, int articleId) {
        this.reactionArticleId = reactionArticleId;
        this.reationType = reationType;
        this.userId = userId;
        this.articleId = articleId;
    }

    public int getReactionArticleId() {
        return reactionArticleId;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isReationType() {
        return reationType;
    }

    public void setReationType(boolean reationType) {
        this.reationType = reationType;
    }

    @Override
    public String toString() {
        return "ReactionArticle{" + "reactionArticleId=" + reactionArticleId + ", articleId=" + articleId + ", userId=" + userId + ", reationType=" + reationType + '}';
    }
}