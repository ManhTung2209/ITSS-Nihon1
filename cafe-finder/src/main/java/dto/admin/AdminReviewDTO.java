package dto.admin;

import com.itss.cafe_finder.model.enums.ReviewStatusType;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AdminReviewDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long cafeId;
    private String cafeName;
    private Integer star;
    private String content;
    private ReviewStatusType status;
    private ZonedDateTime createdAt;
}

