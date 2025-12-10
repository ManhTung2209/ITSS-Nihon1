package dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private Long totalUsers;
    private Long newUsersThisMonth;
    private Long totalCafes;
    private Long totalReviews;
    private Long pendingReviews;
}

