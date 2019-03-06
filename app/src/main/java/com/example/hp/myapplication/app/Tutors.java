package com.example.hp.myapplication.app;

public class Tutors {
    String name,pass,mobile,id,imageUrl;

    public Tutors(String id, String name, String pass, String mobile, String imageUrl){
        id = this.id;
        name = this.name;
        pass = this.pass;
        mobile = this.mobile;
        imageUrl = this.imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
