package com.example.movieapp.models;

public class movieModel {
    String title;
    int movieid;
    String releaseDate;
    String rating;
    String backdropImg;
    public void setMovieid(int movieid){
        this.movieid=movieid;
    }
    public int  getMovieid(){
        return this.movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBackdropImg() {
        return backdropImg;
    }

    public void setBackdropImg(String backdropImg) {
        this.backdropImg = backdropImg;
    }
}
