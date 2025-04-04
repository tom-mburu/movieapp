package com.example.movieapp.models;

public class personModel {
    String profilepiclink;
    String profilename;
    String knownfor;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfilepiclink() {
        return profilepiclink;
    }

    public void setProfilepiclink(String profilepiclink) {
        this.profilepiclink = profilepiclink;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getKnownfor() {
        return knownfor;
    }

    public void setKnownfor(String knownfor) {
        this.knownfor = knownfor;
    }
}
