package com.cafefinder.cafefinderbackend.service;

import com.cafefinder.cafefinderbackend.model.Cafe;
import com.cafefinder.cafefinderbackend.repository.CafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    public Page<Cafe> searchCafeByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cafeRepository.findByNameContainingIgnoreCase(name, pageable);
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}