package dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CafeDTO {
    private Long id;
    private String name;
    private String address;
    private BigDecimal rating;
    private BigDecimal distance;
    private String image;
    private String description;
}

