package com.itss.cafe_finder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itss.cafe_finder.service.impl.CafeService;

import dto.CafeDTO;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    @Autowired
    private CafeService cafeService;

    @GetMapping
    public ResponseEntity<Page<CafeDTO>> getCafes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxDistance,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        return cafeService.searchCafes(
                keyword,
                minRating,
                page,
                size,
                sortBy,
                sortDirection
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<CafeDTO>> getAllCafes() {
        List<CafeDTO> cafes = cafeService.getAllCafes();
        return ResponseEntity.ok(cafes);
    }
}
