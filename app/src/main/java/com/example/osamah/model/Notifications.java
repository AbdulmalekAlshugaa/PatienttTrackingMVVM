package com.example.osamah.model;

public class Notifications {
    private String drEmail,paEmail, comments;

    public Notifications() {
    }

    public Notifications(String drEmail, String paEmail, String comments) {
        this.drEmail = drEmail;
        this.paEmail = paEmail;
        this.comments = comments;
    }

    public String getDrEmail() {
        return drEmail;
    }

    public void setDrEmail(String drEmail) {
        this.drEmail = drEmail;
    }

    public String getPaEmail() {
        return paEmail;
    }

    public void setPaEmail(String paEmail) {
        this.paEmail = paEmail;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
