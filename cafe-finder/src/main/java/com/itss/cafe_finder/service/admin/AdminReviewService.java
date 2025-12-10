package com.itss.cafe_finder.service.admin;

import dto.admin.AdminReviewDTO;
import com.itss.cafe_finder.model.Review;
import com.itss.cafe_finder.model.enums.ReviewStatusType;
import com.itss.cafe_finder.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<AdminReviewDTO> getAllReviews(int page, int size, String keyword, ReviewStatusType status, Integer star) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews;

        if (keyword != null && !keyword.trim().isEmpty()) {
            reviews = reviewRepository.findByContentContainingIgnoreCase(keyword, pageable);
        } else if (status != null && star != null) {
            reviews = reviewRepository.findByStatusAndStar(status, star, pageable);
        } else if (status != null) {
            reviews = reviewRepository.findByStatus(status, pageable);
        } else {
            reviews = reviewRepository.findAll(pageable);
        }

        return reviews.map(this::toDTO);
    }

    public AdminReviewDTO getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public AdminReviewDTO updateReviewStatus(Long id, ReviewStatusType status) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            review.setStatus(status);
            return toDTO(reviewRepository.save(review));
        }
        return null;
    }

    public boolean deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long getPendingReviewsCount() {
        return reviewRepository.countByStatus(ReviewStatusType.pending);
    }

    public long getPublishedReviewsCount() {
        return reviewRepository.countByStatus(ReviewStatusType.published);
    }

    public int approveAllPendingReviews() {
        Pageable pageable = PageRequest.of(0, 10000); // Large page size to get all
        Page<Review> pendingReviews = reviewRepository.findByStatus(ReviewStatusType.pending, pageable);
        
        int count = 0;
        for (Review review : pendingReviews.getContent()) {
            review.setStatus(ReviewStatusType.published);
            reviewRepository.save(review);
            count++;
        }
        
        return count;
    }

    private AdminReviewDTO toDTO(Review review) {
        AdminReviewDTO dto = new AdminReviewDTO();
        dto.setId(review.getId());
        if (review.getUser() != null) {
            dto.setUserId(review.getUser().getId());
            dto.setUserName(review.getUser().getName());
            dto.setUserEmail(review.getUser().getEmail());
        }
        if (review.getCafe() != null) {
            dto.setCafeId(review.getCafe().getId());
            dto.setCafeName(review.getCafe().getName());
        }
        dto.setStar(review.getStar());
        dto.setContent(review.getContent());
        dto.setStatus(review.getStatus());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}

