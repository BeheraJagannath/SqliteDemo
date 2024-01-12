package com.example.squlitetutorial;

public class Event {

    private String Name;
    private String email;
    private String mobile;
    private String gender;
    private int id;
    public Event(String Name, String email, String mobile, String gender) {
        this.Name = Name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
