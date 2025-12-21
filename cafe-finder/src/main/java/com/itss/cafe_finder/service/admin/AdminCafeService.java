package com.itss.cafe_finder.service.admin;

import dto.admin.AdminCafeDTO;
import dto.admin.DishDTO;
import com.itss.cafe_finder.model.Cafe;
import com.itss.cafe_finder.model.Dish;
import com.itss.cafe_finder.model.enums.CafeStatusType;
import com.itss.cafe_finder.repository.CafeRepository;
import com.itss.cafe_finder.repository.DishRepository;
import com.itss.cafe_finder.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminCafeService {

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private DishRepository dishRepository;

    public Page<AdminCafeDTO> getAllCafes(int page, int size, String keyword, CafeStatusType status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cafe> cafes;

        if (keyword != null && !keyword.trim().isEmpty()) {
            cafes = cafeRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
                    keyword, keyword, pageable);
        } else {
            cafes = cafeRepository.findAll(pageable);
        }

        return cafes.map(cafe -> toDTO(cafe, status));
    }

    public AdminCafeDTO getCafeById(Long id) {
        return cafeRepository.findById(id)
                .map(cafe -> toDTO(cafe, null))
                .orElse(null);
    }

    public AdminCafeDTO updateCafe(Long id, AdminCafeDTO dto) {
        Cafe cafe = cafeRepository.findById(id).orElse(null);
        if (cafe != null) {
            if (dto.getName() != null) cafe.setName(dto.getName());
            if (dto.getAddress() != null) cafe.setAddress(dto.getAddress());
            if (dto.getRating() != null) cafe.setRating(dto.getRating());
            if (dto.getDistance() != null) cafe.setDistance(dto.getDistance());
            if (dto.getImage() != null) cafe.setImage(dto.getImage());
            if (dto.getDescription() != null) cafe.setDescription(dto.getDescription());
            if (dto.getTime() != null) cafe.setTime(dto.getTime());
            if (dto.getStatus() != null) cafe.setStatus(dto.getStatus());
            cafe.setUpdatedOn(java.time.ZonedDateTime.now());
            Cafe saved = cafeRepository.save(cafe);

            // Replace dishes if provided
            if (dto.getDishes() != null) {
                dishRepository.deleteByCafeId(saved.getId());
                saveDishes(saved, dto.getDishes());
            }

            return toDTO(saved, null);
        }
        return null;
    }

    public AdminCafeDTO createCafe(AdminCafeDTO dto) {
        Cafe cafe = new Cafe();
        cafe.setName(dto.getName());
        cafe.setAddress(dto.getAddress());
        cafe.setRating(dto.getRating() != null ? dto.getRating() : java.math.BigDecimal.ZERO);
        cafe.setDistance(dto.getDistance() != null ? dto.getDistance() : java.math.BigDecimal.ZERO);
        cafe.setImage(dto.getImage());
        cafe.setDescription(dto.getDescription());
        cafe.setTime(dto.getTime());
        cafe.setStatus(dto.getStatus() != null ? dto.getStatus() : CafeStatusType.opening);
        cafe.setUpdatedOn(java.time.ZonedDateTime.now());

        Cafe saved = cafeRepository.save(cafe);
        saveDishes(saved, dto.getDishes());

        return toDTO(saved, null);
    }

    public boolean deleteCafe(Long id) {
        if (cafeRepository.existsById(id)) {
            cafeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public DishDTO updateDish(Long id, DishDTO dto) {
        Dish dish = dishRepository.findById(id).orElse(null);
        if (dish != null) {
            if (dto.getName() != null) dish.setName(dto.getName());
            if (dto.getPrice() != null) dish.setPrice(dto.getPrice());
            if (dto.getDescription() != null) dish.setDescription(dto.getDescription());
            if (dto.getImage() != null) dish.setImage(dto.getImage());
            dish.setUpdatedOn(ZonedDateTime.now());
            return toDishDTO(dishRepository.save(dish));
        }
        return null;
    }

    private void saveDishes(Cafe cafe, List<DishDTO> dishesDto) {
        if (dishesDto == null || dishesDto.isEmpty()) {
            return;
        }
        List<Dish> dishes = new ArrayList<>();
        for (DishDTO dto : dishesDto) {
            if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                continue;
            }
            Dish dish = new Dish();
            dish.setCafe(cafe);
            dish.setName(dto.getName());
            dish.setPrice(dto.getPrice() != null ? dto.getPrice() : BigDecimal.ZERO);
            dish.setDescription(dto.getDescription());
            dish.setImage(dto.getImage());
            dish.setUpdatedOn(ZonedDateTime.now());
            dishes.add(dish);
        }
        if (!dishes.isEmpty()) {
            dishRepository.saveAll(dishes);
        }
    }

    private AdminCafeDTO toDTO(Cafe cafe, CafeStatusType statusFilter) {
        AdminCafeDTO dto = new AdminCafeDTO();
        dto.setId(cafe.getId());
        dto.setName(cafe.getName());
        dto.setAddress(cafe.getAddress());
        dto.setRating(cafe.getRating());
        dto.setDistance(cafe.getDistance());
        dto.setImage(cafe.getImage());
        dto.setDescription(cafe.getDescription());
        dto.setTime(cafe.getTime());
        dto.setStatus(cafe.getStatus());
        dto.setUpdatedOn(cafe.getUpdatedOn());
        
        // Count reviews for this cafe
        long reviewCount = reviewRepository.findAll().stream()
                .filter(r -> r.getCafe() != null && r.getCafe().getId().equals(cafe.getId()))
                .count();
        dto.setReviewCount(reviewCount);
        
        // Load dishes for this cafe
        List<Dish> dishes = dishRepository.findByCafeId(cafe.getId());
        dto.setDishes(dishes.stream().map(this::toDishDTO).collect(Collectors.toList()));
        
        return dto;
    }

    private DishDTO toDishDTO(Dish dish) {
        DishDTO dto = new DishDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setPrice(dish.getPrice());
        dto.setDescription(dish.getDescription());
        dto.setImage(dish.getImage());
        dto.setUpdatedOn(dish.getUpdatedOn());
        return dto;
    }
}
