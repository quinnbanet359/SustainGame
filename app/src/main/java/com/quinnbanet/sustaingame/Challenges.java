package com.quinnbanet.sustaingame;

/**
 * Created by quinnbanet on 12/5/16.
 */

public class Challenges {
    private long id;
    private long loses;
    private String mascot;
    private String name;
    private String picture;
    private long wins;

    public Challenges(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLoses() {
        return loses;
    }

    public void setLoses(long loses) {
        this.loses = loses;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", loses=" + loses +
                ", mascot='" + mascot + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", wins=" + wins +
                '}';
    }


    public Challenges(long id, long loses, String mascot, String name, String picture, long wins) {
        this.id = id;
        this.loses = loses;
        this.mascot = mascot;
        this.name = name;
        this.picture = picture;
        this.wins = wins;
    }



}
