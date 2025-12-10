package com.itss.cafe_finder.service.admin;

import dto.admin.DashboardStatsDTO;
import com.itss.cafe_finder.model.enums.ReviewStatusType;
import com.itss.cafe_finder.repository.CafeRepository;
import com.itss.cafe_finder.repository.ReviewRepository;
import com.itss.cafe_finder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        // Total users
        stats.setTotalUsers(userRepository.count());
        
        // New users this month (simplified - count all users for now)
        // In production, you'd filter by created date
        stats.setNewUsersThisMonth(userRepository.count());
        
        // Total cafes
        stats.setTotalCafes(cafeRepository.count());
        
        // Total reviews
        stats.setTotalReviews(reviewRepository.count());
        
        // Pending reviews
        stats.setPendingReviews(reviewRepository.countByStatus(ReviewStatusType.pending));
        
        return stats;
    }
}

