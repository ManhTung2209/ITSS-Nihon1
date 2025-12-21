package com.itss.cafe_finder.service;

import com.itss.cafe_finder.model.Cafe;
import com.itss.cafe_finder.model.Dish;
import com.itss.cafe_finder.model.Review;
import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.repository.CafeRepository;
import com.itss.cafe_finder.repository.DishRepository;
import com.itss.cafe_finder.repository.ReviewRepository;
import dto.CafeDTO;
import dto.DishDTO;
import dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CafeDetailService {

    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public CafeDTO getCafeDetail(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElse(null);
        if (cafe == null) return null;

        CafeDTO dto = new CafeDTO();
        dto.setId(cafe.getId());
        dto.setName(cafe.getName());
        dto.setAddress(cafe.getAddress());
        dto.setRating(cafe.getRating());
        dto.setDistance(cafe.getDistance());
        dto.setDescription(cafe.getDescription());
        dto.setImage(cafe.getImage());
        dto.setStatus(cafe.getStatus() != null ? cafe.getStatus().toString() : "opening");
        return dto;
    }

    public List<DishDTO> getDishes(Long cafeId) {
        List<Dish> dishes = dishRepository.findByCafeId(cafeId);
        return dishes.stream().map(item -> {
            DishDTO dto = new DishDTO();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setPrice(item.getPrice());
            dto.setDescription(item.getDescription());
            dto.setImage(item.getImage());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviews(Long cafeId) {
        List<Review> reviews = reviewRepository.findByCafeId(cafeId);
        return reviews.stream().map(item -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(item.getId());
            if (item.getUser() != null) {
                dto.setUserName(item.getUser().getName());
            }
            dto.setStar(item.getStar());
            dto.setContent(item.getContent());
            dto.setCreatedAt(item.getCreatedAt());
            return dto;
        }).toList();
    }
    
    public void saveReview(Long cafeId, User user, Integer star, String content) {
        Cafe cafe = cafeRepository.findById(cafeId).orElse(null);
        if (cafe == null) return;
        
        Review review = new Review();
        review.setCafe(cafe);
        review.setUser(user);
        review.setStar(star);
        review.setContent(content);
        review.setCreatedAt(ZonedDateTime.now());
        
        reviewRepository.save(review);
    }
}