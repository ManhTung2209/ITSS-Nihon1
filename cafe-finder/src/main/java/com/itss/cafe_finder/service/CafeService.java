package com.itss.cafe_finder.service;

import com.itss.cafe_finder.model.Cafe;
import com.itss.cafe_finder.repository.CafeRepository;
import dto.CafeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            int size) {

        String searchName = Optional.ofNullable(keyword)
                .map(String::trim)
                .orElse("");

        boolean hasKeyword = !searchName.isEmpty();

        BigDecimal minRatingBd = Optional.ofNullable(minRating)
                .map(BigDecimal::valueOf)
                .orElse(null);

        boolean hasRating = minRatingBd != null;

        BigDecimal maxDistanceBd = Optional.ofNullable(maxDistance)
                .map(BigDecimal::valueOf)
                .orElse(null);

        boolean hasDistance = maxDistanceBd != null;

        Pageable pageable = PageRequest.of(page, size);
        Page<Cafe> cafes;

        if (hasKeyword && hasRating && hasDistance) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCaseAndRatingGreaterThanEqualAndDistanceLessThanEqual(
                            searchName, minRatingBd, maxDistanceBd, pageable);
        }
        else if (hasKeyword && hasRating) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCaseAndRatingGreaterThanEqual(
                            searchName, minRatingBd, pageable);
        }
        else if (hasKeyword && hasDistance) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCaseAndDistanceLessThanEqual(
                            searchName, maxDistanceBd, pageable);
        }
        else if (hasKeyword) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCase(searchName, pageable);
        }
        else if (hasRating && hasDistance) {
            cafes = cafeRepository
                    .findByRatingGreaterThanEqualAndDistanceLessThanEqual(
                            minRatingBd, maxDistanceBd, pageable);
        }
        else if (hasRating) {
            cafes = cafeRepository
                    .findByRatingGreaterThanEqual(minRatingBd, pageable);
        }
        else if (hasDistance) {
            cafes = cafeRepository
                    .findByDistanceLessThanEqual(maxDistanceBd, pageable);
        }
        else {
            cafes = cafeRepository.findAll(pageable);
        }

        Page<CafeDTO> dtoPage = cafes.map(this::toDTO);
        return ResponseEntity.ok(dtoPage);
    }


    public ResponseEntity<Page<CafeDTO>> getAllCafe(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cafe> cafes = cafeRepository.findAll(pageable);
        Page<CafeDTO> dtos = cafes.map(this::toDTO);
        return ResponseEntity.ok(dtos);
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
        return dto;
    }
}
