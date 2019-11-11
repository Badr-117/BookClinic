package com.example.segproject2019.User;

public class User {

    public String id, username,userEmail, password, userType;

    public User(){}

    public User(String id, String username,String userEmail,String password,  String userType){
        this.id = id;
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
        this.userType = userType;
    }

    public String getUserId() {
        return id;
    }

    public void setUserId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
