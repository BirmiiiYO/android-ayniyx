package com.example.lxst.Model;

import com.example.lxst.views.ResultFragment;

public class ResultModel {

    private int correct ,wrong, notAnswered;

    private String user;

    public ResultModel(){}

    public int getWrong() {
        return wrong;
    }

    public int getCorrect() {
        return correct;
    }

    public int getNotAnswered() {
        return notAnswered;
    }

    public String getUser() {
        return user;
    }
}