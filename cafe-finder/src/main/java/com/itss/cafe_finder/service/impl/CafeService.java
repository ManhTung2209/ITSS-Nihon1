package com.itss.cafe_finder.service.impl;

import com.itss.cafe_finder.model.Cafe;
import com.itss.cafe_finder.repository.CafeRepository;
import dto.CafeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    public ResponseEntity<Page<CafeDTO>> searchCafes(
            String keyword,
            Double minRating,
            Double maxDistance,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {

        String searchName = Optional.ofNullable(keyword)
                .map(String::trim)
                .orElse("");

        BigDecimal minRatingBd = minRating != null ? BigDecimal.valueOf(minRating) : null;
        BigDecimal maxDistanceBd = maxDistance != null ? BigDecimal.valueOf(maxDistance) : null;

        boolean hasKeyword = !searchName.isEmpty();
        boolean hasRating = minRatingBd != null;
        boolean hasDistance = maxDistanceBd != null;

        Sort sort = Sort.unsorted();

        if (sortBy != null && !sortBy.isBlank()) {

            Sort.Direction direction =
                    "desc".equalsIgnoreCase(sortDirection)
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;

            switch (sortBy) {
                case "rating":
                    sort = Sort.by(direction, "rating");
                    break;

                case "distance":
                    sort = Sort.by(direction, "distance");
                    break;

                default:
                    sort = Sort.by(direction, sortBy);
            }
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Cafe> cafes;

        if (hasKeyword && hasRating && hasDistance) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCaseAndRatingGreaterThanEqualAndDistanceLessThanEqual(
                            searchName, minRatingBd, maxDistanceBd, pageable);

        } else if (hasKeyword && hasRating) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCaseAndRatingGreaterThanEqual(
                            searchName, minRatingBd, pageable);

        } else if (hasKeyword && hasDistance) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCaseAndDistanceLessThanEqual(
                            searchName, maxDistanceBd, pageable);

        } else if (hasKeyword) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCase(searchName, pageable);

        } else if (hasRating && hasDistance) {
            cafes = cafeRepository
                    .findByRatingGreaterThanEqualAndDistanceLessThanEqual(
                            minRatingBd, maxDistanceBd, pageable);

        } else if (hasRating) {
            cafes = cafeRepository
                    .findByRatingGreaterThanEqual(minRatingBd, pageable);

        } else if (hasDistance) {
            cafes = cafeRepository
                    .findByDistanceLessThanEqual(maxDistanceBd, pageable);

        } else {
            cafes = cafeRepository.findAll(pageable);
        }

        return ResponseEntity.ok(cafes.map(this::toDTO));
    }

    public ResponseEntity<Page<CafeDTO>> getAllCafe(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cafe> cafes = cafeRepository.findAll(pageable);
        return ResponseEntity.ok(cafes.map(this::toDTO));
    }

    private CafeDTO toDTO(Cafe c) {
        CafeDTO dto = new CafeDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setAddress(c.getAddress());
        dto.setRating(c.getRating());
        dto.setDistance(c.getDistance());
        dto.setDescription(c.getDescription());
        dto.setImage(c.getImage());
        dto.setStatus(c.getStatus() != null ? c.getStatus().toString() : "opening");
        return dto;
    }
}