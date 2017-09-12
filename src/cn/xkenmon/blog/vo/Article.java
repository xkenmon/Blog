package cn.xkenmon.blog.vo;

import java.util.Date;

public class Article {

    private int id;
    private String title;
    private String type;
    private Date date;
    private Date updateTime;
    private String summary;
    private String content;
    private int readCount = -1;
    private String author;
    private String cover;
    private String readTime;

    //Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    //
    private String getIntroduction(int end) {
        if (end >= content.length())
            end = content.length()-1;
        if (end <0)
            end = 0;
        return getContent().substring(0, end);
    }

    @Override
    public String toString() {
        return "\n-----------------------------" +
                "\nTitle: " + title +
                "\nType: " + type +
                "\nSummary: " + summary +
                "\nAuthor: " + author +
                "\nDate: " + date +
                "\nUpdate: " + updateTime +
                "\ncover: " + cover +
                "\nIntroduction: " + getIntroduction(100) +
                "\n-----------------------------";
    }
}
