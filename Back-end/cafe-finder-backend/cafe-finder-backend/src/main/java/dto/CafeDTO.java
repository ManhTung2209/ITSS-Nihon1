package dto;

import java.math.BigDecimal;

public class CafeDTO {
    private Integer cafeID;
    private String name;
    private String address;
    private BigDecimal rating;
    private String description;
    private String image;

    // Getters và Setters
    public Integer getCafeID() { return cafeID; }
    public void setCafeID(Integer cafeID) { this.cafeID = cafeID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
