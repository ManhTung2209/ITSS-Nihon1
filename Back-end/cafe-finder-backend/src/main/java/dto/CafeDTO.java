package dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CafeDTO {
    private Integer cafeID;
    private String name;
    private String address;
    private BigDecimal rating;
    private String image;
    private String description;
}