package com.itss.cafe_finder.repository;

import com.itss.cafe_finder.model.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    Page<Cafe> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<Cafe> findByNameContainingIgnoreCaseAndRatingGreaterThanEqual(
            String name,
            BigDecimal minRating,
            Pageable pageable
    );

    Page<Cafe> findByRatingGreaterThanEqual(
            BigDecimal rating,
            Pageable pageable
    );

    Page<Cafe> findByDistanceLessThanEqual(
            BigDecimal maxDistance,
            Pageable pageable
    );

    Page<Cafe> findByNameContainingIgnoreCaseAndDistanceLessThanEqual(
            String name,
            BigDecimal maxDistance,
            Pageable pageable
    );

    Page<Cafe> findByRatingGreaterThanEqualAndDistanceLessThanEqual(
            BigDecimal minRating,
            BigDecimal maxDistance,
            Pageable pageable
    );

    Page<Cafe> findByNameContainingIgnoreCaseAndRatingGreaterThanEqualAndDistanceLessThanEqual(
            String name,
            BigDecimal minRating,
            BigDecimal maxDistance,
            Pageable pageable
    );
}
