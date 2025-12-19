package dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private String userName; // hoặc userEmail nếu muốn
    private Integer star;
    private String content;
    private ZonedDateTime createdAt;
}