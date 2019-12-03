package com.example.myapplication.model.pojo;

import org.litepal.crud.DataSupport;

import java.util.Date;

public class Idea extends DataSupport {
    //胶囊ID、拥有者ID、胶囊名称（标题）、胶囊内容、胶囊生成时间
    private int ideaId;
    private int ownerId;
    private String title;
    private String content;
    private Date date;
    private boolean is_upload;
    private String ownerName;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
