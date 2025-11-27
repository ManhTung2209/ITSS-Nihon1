package com.cafefinder.cafefinderbackend.service;

import com.cafefinder.cafefinderbackend.model.Cafe;
import com.cafefinder.cafefinderbackend.repository.CafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    public Page<Cafe> searchCafeByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cafeRepository.findByNameContainingIgnoreCase(name, pageable);
    }
    
    public List<Cafe> searchCafes(String keyword, Double minRating) {

        List<Cafe> allCafes = cafeRepository.findAll();
        
        List<Cafe> filteredCafes = allCafes.stream()
            .filter(cafe -> {
                if (minRating == null) {
                    return true;
                }
                
                BigDecimal minRatingBd = BigDecimal.valueOf(minRating);
                
                return cafe.getRating() != null && cafe.getRating().compareTo(minRatingBd) >= 0;
            })
            
            .filter(cafe -> {
                if (keyword == null || keyword.trim().isEmpty()) {
                    return true;
                }
                String lowerKeyword = keyword.toLowerCase();
                String cafeName = cafe.getName() != null ? cafe.getName().toLowerCase() : "";
                String cafeAddress = cafe.getAddress() != null ? cafe.getAddress().toLowerCase() : "";

                return cafeName.contains(lowerKeyword) || cafeAddress.contains(lowerKeyword);
            })
            .collect(Collectors.toList());

        return filteredCafes;
    }
}