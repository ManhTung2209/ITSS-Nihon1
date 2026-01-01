package dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CafeDTO {
    private Long id;
    private String name;
    private String address;
    private BigDecimal rating;
    private Double lat;
    private Double lng;
    private String image;
    private String description;
    private String status;
    private Double distance;
}