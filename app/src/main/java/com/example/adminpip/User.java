package com.example.adminpip;

import java.io.Serializable;

public class User implements Serializable {

    private int ID;
    private String Name;
    private String Vote_value;
    private String Question;

    public User(){ }

    public User(String name, int ID){
        this.Name=name;
        this.ID=ID;
    }

    public User(String Name, int ID, String vote_value){
        this.Name = Name;
        this.ID = ID;
        Vote_value = vote_value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getVote_value() {
        return Vote_value;
    }

    public void setVote_value(String vote_value) {
        Vote_value = vote_value;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

}
