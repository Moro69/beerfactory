package com.moro.model;

public class BeerDto {

    private Integer beerId;
    private String beerTitle;
    private double beerAbv;
    private String description;
    private int beerPrice;
    private String beerImage;
    private double rating;

    public BeerDto() {
    }

    public BeerDto(Integer beerId,
                   String beerTitle, double beerAbv,
                        String description, int beerPrice, double rating,
                            String beerImage) {
        this.beerId = beerId;
        this.beerTitle = beerTitle;
        this.beerAbv = beerAbv;
        this.description = description;
        this.beerPrice = beerPrice;
        this.rating = rating;
        this.beerImage = beerImage;
    }

    public Integer getBeerId() {
        return beerId;
    }

    public void setBeerId(Integer beerId) {
        this.beerId = beerId;
    }

    public String getBeerTitle() {
        return beerTitle;
    }

    public void setBeerTitle(String beerTitle) {
        this.beerTitle = beerTitle;
    }

    public double getBeerAbv() {
        return beerAbv;
    }

    public void setBeerAbv(double beerAbv) {
        this.beerAbv = beerAbv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBeerPrice() {
        return beerPrice;
    }

    public void setBeerPrice(int beerPrice) {
        this.beerPrice = beerPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBeerImage() {
        return beerImage;
    }

    public void setBeerImage(String beerImage) {
        this.beerImage = beerImage;
    }

    @Override
    public String toString() {
        return "BeerDto{"
                + "beerId=" + beerId
                + ", beerTitle='" + beerTitle + '\''
                + ", beerAbv=" + beerAbv
                + ", description='" + description + '\''
                + ", beerPrice=" + beerPrice
                + ", rating=" + rating
                + '}';
    }
}
