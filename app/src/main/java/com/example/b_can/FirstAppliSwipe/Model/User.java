package com.example.b_can.FirstAppliSwipe.Model;

/**
 * Created by b_can on 20/02/2015.
 */
public class User {
    private int id;
    private String pseudo;
    private String password;
    private int distance;

    public User(){}

    public User(String pseudo, String password, int distance) {
        super();
        this.pseudo = pseudo;
        this.password = password;
        this.distance = distance;
    }
    //getters & setters


    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public int getDistance() {
        return distance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", pseudo=" + pseudo + ", password=" + password + ", distance=" + distance
                + "]";
    }
}
