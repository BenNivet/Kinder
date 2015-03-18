package com.example.b_can.FirstAppliSwipe.Model;

/**
 * Created by b_can on 11/02/2015.
 */

public class Message {
    private int id;
    private String pseudo;
    private String civilite;
    private String date;
    private String path;

    public Message(){}

    public Message(String pseudo, String civilite, String date, String path) {
        this.pseudo = pseudo;
        this.civilite = civilite;
        this.date = date;
        this.path = path;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
