package com.example.lxst.Model;

import com.google.firebase.firestore.DocumentId;
public class LeaderboardModel {


    private String user;

    private String correct;

    public LeaderboardModel(){}

    public LeaderboardModel(String email, String score) {
        this.user = user;
        this.correct = correct;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
