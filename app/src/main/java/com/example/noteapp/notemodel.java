package com.example.noteapp;

public class notemodel {
    String Title;
    String Note;
    int id;




    public notemodel(String Title, String Note){
        this.Title=Title;
        this.Note= Note;

    }


    public  notemodel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNote() {
        return Note;
    }

    public String setNote(String note) {
        return note;
    }


}
