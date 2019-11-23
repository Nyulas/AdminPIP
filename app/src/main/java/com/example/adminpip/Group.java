package com.example.adminpip;

public class Group {

    private String name;
    private int ID;
    //ez
    private Question question;

    public Group(){

    }

    public Group(String name, int ID, Question question) {
        this.name = name;
        this.ID = ID;
        this.question = question;
    }

    public Group(String name,int ID){
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}
