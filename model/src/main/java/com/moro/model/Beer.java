package com.moro.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * POJO beer for model.
 */
public class Beer {

    /**
     * beer id.
     */
    private Integer beerId;

    /**
     * beer title.
     */
    @NotEmpty(message = "Fill in the field.")
    @Size(min = 2, max = 255, message = "Length must be between 2 and 255.")
    private String beerTitle;

    /**
     * beer abv.
     */
    @PositiveOrZero(message = "Cannot be negative.")
    private double beerAbv;

    /**
     * beer description.
     */
    @NotEmpty(message = "Fill in the field.")
    @Size(min = 2, max = 255, message = "Length must be between 2 and 255.")
    private String description;

    /**
     * beer price.
     */
    @PositiveOrZero(message = "Cannot be negative.")
    private int price;

    private String beerImage;

    /**
     * Constructor with arguments.
     *
     * @param beerTitle beer title.
     * @param beerAbv beer abv.
     * @param description beer description.
     * @param price beer price.
     * @param beerImage name of the beer image.
     */
    public Beer(String beerTitle, double beerAbv, String description, int price, String beerImage) {
        this.beerTitle = beerTitle;
        this.beerAbv = beerAbv;
        this.description = description;
        this.price = price;
        this.beerImage = beerImage;
    }

    /**
     * Constructor with no arguments.
     */
    public Beer(){}

    /**
     * Get beer id.
     * @return beer id.
     */
    public Integer getBeerId() {
        return beerId;
    }

    /**
     * Set beer id.
     * @param beerId beer id.
     */
    public void setBeerId(Integer beerId) {
        this.beerId = beerId;
    }

    /**
     * Get beer title.
     *
     * @return beer title.
     */
    public String getBeerTitle() {
        return beerTitle;
    }

    /**
     * Set beer title.
     *
     * @param beerTitle beer title.
     */
    public void setBeerTitle(String beerTitle) {
        this.beerTitle = beerTitle;
    }

    /**
     * Get beer description.
     *
     * @return beer description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set beer description.
     *
     * @param description beer description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get beer abv.
     *
     * @return beer abv.
     */
    public double getBeerAbv() {
        return beerAbv;
    }

    /**
     * Set beer abv.
     *
     * @param beerAbv beer abv.
     */
    public void setBeerAbv(double beerAbv) {
        this.beerAbv = beerAbv;
    }

    /**
     * Get beer price.
     *
     * @return beer price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Set beer price.
     *
     * @param price beer price.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    public String getBeerImage() {
        return beerImage;
    }

    public void setBeerImage(String beerImage) {
        this.beerImage = beerImage;
    }

    /**
     * Overridden toString method.
     * @return string view of object.
     */
    @Override
    public String toString() {
        return "beer{"
                + "beerId=" + beerId
                + ", beerTitle='" + beerTitle + '\''
                + ", beerAbv=" + beerAbv
                + ", description='" + description + '\''
                + ", price=" + price
                + ", beerImage='" + beerImage + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return Double.compare(beer.beerAbv, beerAbv) == 0 &&
                price == beer.price &&
                Objects.equals(beerId, beer.beerId) &&
                Objects.equals(beerTitle, beer.beerTitle) &&
                Objects.equals(description, beer.description) &&
                Objects.equals(beerImage, beer.beerImage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(beerId, beerTitle, beerAbv, description, price, beerImage);
    }
}
