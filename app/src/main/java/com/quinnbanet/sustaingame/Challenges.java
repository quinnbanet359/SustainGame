package com.quinnbanet.sustaingame;

/**
 * Created by quinnbanet on 12/5/16.
 */

public class Challenges {
    private long id;
    private String startDate;
    private String progress;
    private String name;
    private String picture;
    private String createdBy;
    private String endDate;
    private long utcStartDate;
    private long utcEndDate;

    public Challenges(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getUtcStartDate() {
        return utcStartDate;
    }

    public void setUtcStartDate(long utcStartDate) {
        this.utcStartDate = utcStartDate;
    }

    public double getUtcEndDate() {
        return utcEndDate;
    }

    public void setUtcEndDate(long utcEndDate) {
        this.utcEndDate = utcEndDate;
    }

   @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", progress='" + progress + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", createdBy=" + createdBy +
                ", endDate=" + endDate +
                '}';
    }
/*    @Override
    public String toString() {
        return "Team{" +
                id +
                startDate +
                progress +
                name +
                picture +
                createdBy +
                endDate +
                utcStartDate +
                utcEndDate +
                '}';
    }*/



    public Challenges(long id, String startDate, String progress, String name, String picture, String createdBy, String endDate, long utcStartDate, long utcEndDate) {
        this.id = id;
        this.startDate = startDate;
        this.progress = progress;
        this.name = name;
        this.picture = picture;
        this.createdBy = createdBy;
        this.endDate = endDate;
        this.utcStartDate = utcStartDate;
        this.utcEndDate = utcEndDate;
    }

/* FOR REFERENCE:
    private long id;
    private String startDate;
    private String progress;
    private String name;
    private String picture;
    private String createdBy;
    private String endDate;
    private double utcStartDate;
    private double utcEndDate;
 */

}
