package com.cafefinder.cafefinderbackend.service;

import com.cafefinder.cafefinderbackend.model.Cafe;
import com.cafefinder.cafefinderbackend.repository.CafeRepository;
import dto.CafeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    public ResponseEntity<Page<CafeDTO>> searchCafesByKeywordAndRating(
            String keyword,
            Double minRating,
            int page,
            int size) {

        if (keyword == null) keyword = "";
        keyword = keyword.trim();

        Pageable pageable = PageRequest.of(page, size);
        Page<Cafe> cafes;

        BigDecimal minRatingBd = (minRating != null)
                ? BigDecimal.valueOf(minRating)
                : null;

        if (!keyword.isEmpty() && minRatingBd != null) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCaseAndRatingGreaterThanEqual(
                            keyword, minRatingBd, pageable);
        }
        else if (!keyword.isEmpty()) {
            cafes = cafeRepository
                    .findByNameContainingIgnoreCase(keyword, pageable);
        }
        else if (minRatingBd != null) {
            cafes = cafeRepository
                    .findByRatingGreaterThanEqual(minRatingBd, pageable);
        }
        else {
            cafes = cafeRepository.findAll(pageable);
        }

        Page<CafeDTO> cafeDTOs = cafes.map(this::toDTO);

        return ResponseEntity.ok(cafeDTOs);
    }

    public ResponseEntity<Page<CafeDTO>> getAllCafe(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cafe> cafes = cafeRepository.findAll(pageable);
        Page<CafeDTO> dtos = cafes.map(this::toDTO);
        return ResponseEntity.ok(dtos);
    }

    private CafeDTO toDTO(Cafe c) {
        CafeDTO dto = new CafeDTO();
        dto.setCafeID(c.getCafeID());
        dto.setName(c.getName());
        dto.setAddress(c.getAddress());
        dto.setRating(c.getRating());
        return dto;
    }
}
