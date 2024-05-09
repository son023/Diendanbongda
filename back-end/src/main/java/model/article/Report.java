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
public class Report {
    private int reportId, userId, articleId;
    private String content;

    public Report(int reportId, int userId, int articleId, String content) {
        this.reportId = reportId;
        this.userId = userId;
        this.articleId = articleId;
        this.content = content;
    }

    public int getReportId() {
        return reportId;
    }

    public int getUserId() {
        return userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Report{" + "reportId=" + reportId + ", userId=" + userId + ", articleId=" + articleId + ", content=" + content + '}';
    }
}