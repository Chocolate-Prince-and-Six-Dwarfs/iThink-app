package com.example.myapplication.model.pojo;

public class Idea {
    private int ideaId;
    private String title;
    private String content;

    public Idea(int ideaId , String title , String content){
        this.ideaId = ideaId;
        this.title = title;
        this.content = content;
    }

    public String getTitle(){
        return  title;
    }
    public String getContent() {
        return  content;
    }
    public int getIdeaId(){
        return ideaId;
    }
}
