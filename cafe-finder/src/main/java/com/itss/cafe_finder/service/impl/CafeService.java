package com.itss.cafe_finder.service.impl;

import com.itss.cafe_finder.model.Cafe;
import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.repository.CafeRepository;
import com.itss.cafe_finder.repository.UserRepository;
import dto.CafeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Page<CafeDTO>> searchCafes(
            String keyword,
            Double minRating,
            Double maxDistance,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        String searchName = Optional.ofNullable(keyword).map(String::trim).orElse("");
        String keywordParam = searchName.isEmpty() ? null : searchName;
        BigDecimal minRatingBd = minRating != null ? BigDecimal.valueOf(minRating) : null;

        User currentUser = getCurrentUser();
        
        boolean hasUserLocation = currentUser != null && currentUser.getLat() != null && currentUser.getLng() != null;
        
        if ("distance".equalsIgnoreCase(sortBy) && !hasUserLocation) {
            sortBy = "id";
        }

        Page<Cafe> cafes;

        if ("distance".equalsIgnoreCase(sortBy) && hasUserLocation) {
            
            Pageable pageable = PageRequest.of(page, size);
            
            cafes = cafeRepository.searchCafesSortedByDistance(
                    keywordParam,
                    minRatingBd,
                    currentUser.getLat(),
                    currentUser.getLng(),
                    maxDistance,
                    pageable
            );
        } else {
            
            Sort sort = Sort.unsorted();
            if (sortBy != null && !sortBy.isBlank()) {
                Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
                if ("rating".equalsIgnoreCase(sortBy)) {
                    sort = Sort.by(direction, "rating");
                } else {
                    sort = Sort.by(direction, sortBy);
                }
            }
            Pageable pageable = PageRequest.of(page, size, sort);

            boolean filterByDistance = maxDistance != null && hasUserLocation;

            if (filterByDistance) {
                cafes = cafeRepository.searchCafesWithDistance(
                        keywordParam, 
                        minRatingBd, 
                        currentUser.getLat(), 
                        currentUser.getLng(), 
                        maxDistance, 
                        pageable
                );
            } else {
                if (!searchName.isEmpty() && minRatingBd != null) {
                    cafes = cafeRepository.findByNameContainingIgnoreCaseAndRatingGreaterThanEqual(searchName, minRatingBd, pageable);
                } else if (!searchName.isEmpty()) {
                    cafes = cafeRepository.findByNameContainingIgnoreCase(searchName, pageable);
                } else if (minRatingBd != null) {
                    cafes = cafeRepository.findByRatingGreaterThanEqual(minRatingBd, pageable);
                } else {
                    cafes = cafeRepository.findAll(pageable);
                }
            }
        }

        return ResponseEntity.ok(cafes.map(cafe -> toDTO(cafe, currentUser)));
    }

    public ResponseEntity<Page<CafeDTO>> getAllCafe(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cafe> cafes = cafeRepository.findAll(pageable);
        User currentUser = getCurrentUser();
        return ResponseEntity.ok(cafes.map(cafe -> toDTO(cafe, currentUser)));
    }

    private CafeDTO toDTO(Cafe c, User currentUser) {
        CafeDTO dto = new CafeDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setAddress(c.getAddress());
        dto.setRating(c.getRating());
        dto.setDescription(c.getDescription());
        dto.setImage(c.getImage());
        dto.setStatus(c.getStatus() != null ? c.getStatus().toString() : "opening");

        if (currentUser == null) {
            dto.setLat(null);
            dto.setLng(null);
            dto.setDistance(null);
        } else {
            dto.setLat(c.getLat());
            dto.setLng(c.getLng());
            
            if (currentUser.getLat() != null && currentUser.getLng() != null 
                && c.getLat() != null && c.getLng() != null) {
                
                double dist = calculateHaversineDistance(
                    currentUser.getLat(), currentUser.getLng(),
                    c.getLat(), c.getLng()
                );
                dto.setDistance(Math.round(dist * 10.0) / 10.0);
            } else {
                dto.setDistance(null);
            }
        }
        return dto;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() 
            && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

    private double calculateHaversineDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}