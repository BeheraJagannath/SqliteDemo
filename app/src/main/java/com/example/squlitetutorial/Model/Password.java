package com.example.squlitetutorial.Model;

import android.content.Context;

import io.paperdb.Paper;

public class Password {
    private String PASSWORD_KEY="PASSWORD KEY";
    public String PATTERN_SET="Pattern doesn't match";
    public String CONFORM_PATTERN="Draw the pattern again to conform";
    public String INCORRECT_PATTERN = "Incorrect pattern draw";
    public String FIRST_USE = "Draw an unlock pattern please";
    public String SCHEMA_FAILED = "You must at least connect 4 dots !";
    public Boolean isFirst=true ;


    public Password(Context context){
        Paper.init(context);
    }

    public String getPASSWORD_KEY() {
        return Paper.book().read(PASSWORD_KEY);
    }

    public void setPASSWORD_KEY(String pass) {
        Paper.book().write(PASSWORD_KEY,pass);
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }
    public boolean isCorrect(String pass){
        return pass.equals(getPASSWORD_KEY());
    }
}
