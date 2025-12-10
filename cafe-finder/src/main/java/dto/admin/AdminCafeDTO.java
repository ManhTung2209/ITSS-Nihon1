package dto.admin;

import com.itss.cafe_finder.model.enums.CafeStatusType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class AdminCafeDTO {
    private Long id;
    private String name;
    private String address;
    private BigDecimal rating;
    private BigDecimal distance;
    private String image;
    private String description;
    private String time;
    private CafeStatusType status;
    private ZonedDateTime updatedOn;
    private Long reviewCount;
    private List<DishDTO> dishes;
}

