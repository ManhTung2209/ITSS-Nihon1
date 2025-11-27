package dto;

import lombok.Data;

import java.math.BigDecimal;

public class CafeDTO {
    private Integer cafeID;
    private String name;
    private String address;
    private BigDecimal rating;

    public Integer getCafeID() {
        return cafeID;
    }

    public void setCafeID(Integer cafeID) {
        this.cafeID = cafeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}

