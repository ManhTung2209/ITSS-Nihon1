package com.cafefinder.cafefinderbackend.controller;

import com.cafefinder.cafefinderbackend.service.CafeService;
import dto.CafeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    @Autowired
    private CafeService cafeService;

    @GetMapping("/search")
    public ResponseEntity<Page<CafeDTO>> searchCafe(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(required = false) Double minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return cafeService.searchCafesByKeywordAndRating(keyword, minRating, page, size);
    }

    @GetMapping
    public ResponseEntity<Page<CafeDTO>> getALLCafe(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return cafeService.getAllCafe(page, size);
    }
}