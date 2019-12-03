package com.example.myapplication.model.pojo;

import org.litepal.crud.DataSupport;

public class Idea extends DataSupport {
    //胶囊ID、拥有者ID、胶囊名称（标题）、胶囊内容、胶囊生成时间
    private int ideaId;
    private int ownerId;
    private String title;
    private String content;
    private String date;
    private boolean is_upload;

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_upload() {
        return is_upload;
    }

    public void setIs_upload(boolean is_upload) {
        this.is_upload = is_upload;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
