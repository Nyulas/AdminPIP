package com.example.adminpip;

import java.util.ArrayList;

public class Group {

    public String name;
    public int ID;
   // public ArrayList<Question> questions;

    public Group(){

    }

    public Group(String name, int ID){//, ArrayList<Question> questions) {
        this.name = name;
        this.ID = ID;
      //  this.questions = questions;
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

    /*public void addQuestion(Question question){
        questions.add(question);
    }*/
}
