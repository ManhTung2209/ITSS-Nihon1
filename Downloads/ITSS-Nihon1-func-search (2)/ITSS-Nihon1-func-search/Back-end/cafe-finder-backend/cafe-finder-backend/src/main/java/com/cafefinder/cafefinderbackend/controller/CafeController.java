package com.cafefinder.cafefinderbackend.controller;

import com.cafefinder.cafefinderbackend.model.Cafe;
import com.cafefinder.cafefinderbackend.service.CafeService;
import dto.CafeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    @Autowired
    private CafeService cafeService;

    @GetMapping("/search")
    public ResponseEntity<Page<CafeDTO>> searchCafe(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return cafeService.searchCafeByName(name, page, size);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CafeDTO>> getALLCafe(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return cafeService.getAllCafe(page, size);
    }
}
