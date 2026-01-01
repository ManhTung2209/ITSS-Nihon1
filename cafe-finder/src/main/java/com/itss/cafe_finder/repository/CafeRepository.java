package com.itss.cafe_finder.repository;

import com.itss.cafe_finder.model.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    Page<Cafe> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Cafe> findByNameContainingIgnoreCaseAndRatingGreaterThanEqual(
            String name, BigDecimal minRating, Pageable pageable
    );

    Page<Cafe> findByRatingGreaterThanEqual(BigDecimal rating, Pageable pageable);

    Page<Cafe> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
            String name, String address, Pageable pageable);
    
    @Query(value = "SELECT * FROM cafes c " +
            "WHERE (:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:minRating IS NULL OR c.rating >= :minRating) " +
            "AND ( " +
            "   6371 * acos( " +
            "     cos(radians(:userLat)) * cos(radians(c.lat)) * " +
            "     cos(radians(c.lng) - radians(:userLng)) + " +
            "     sin(radians(:userLat)) * sin(radians(c.lat)) " +
            "   ) " +
            ") <= :maxDistance",
            
            countQuery = "SELECT count(*) FROM cafes c " +
            "WHERE (:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:minRating IS NULL OR c.rating >= :minRating) " +
            "AND ( " +
            "   6371 * acos( " +
            "     cos(radians(:userLat)) * cos(radians(c.lat)) * " +
            "     cos(radians(c.lng) - radians(:userLng)) + " +
            "     sin(radians(:userLat)) * sin(radians(c.lat)) " +
            "   ) " +
            ") <= :maxDistance",
            nativeQuery = true)
    Page<Cafe> searchCafesWithDistance(
            @Param("keyword") String keyword,
            @Param("minRating") BigDecimal minRating,
            @Param("userLat") Double userLat,
            @Param("userLng") Double userLng,
            @Param("maxDistance") Double maxDistance,
            Pageable pageable
    );

    @Query(value = "SELECT * FROM cafes c " +
            "WHERE (:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:minRating IS NULL OR c.rating >= :minRating) " +
            "AND (:maxDistance IS NULL OR ( " +
            "   6371 * acos( " +
            "     cos(radians(:userLat)) * cos(radians(c.lat)) * " +
            "     cos(radians(c.lng) - radians(:userLng)) + " +
            "     sin(radians(:userLat)) * sin(radians(c.lat)) " +
            "   ) " +
            ") <= :maxDistance) " +
            "ORDER BY ( " +
            "   6371 * acos( " +
            "     cos(radians(:userLat)) * cos(radians(c.lat)) * " +
            "     cos(radians(c.lng) - radians(:userLng)) + " +
            "     sin(radians(:userLat)) * sin(radians(c.lat)) " +
            "   ) " +
            ") ASC",
            
            countQuery = "SELECT count(*) FROM cafes c " +
            "WHERE (:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:minRating IS NULL OR c.rating >= :minRating) " +
            "AND (:maxDistance IS NULL OR ( " +
            "   6371 * acos( " +
            "     cos(radians(:userLat)) * cos(radians(c.lat)) * " +
            "     cos(radians(c.lng) - radians(:userLng)) + " +
            "     sin(radians(:userLat)) * sin(radians(c.lat)) " +
            "   ) " +
            ") <= :maxDistance)",
            nativeQuery = true)
    Page<Cafe> searchCafesSortedByDistance(
            @Param("keyword") String keyword,
            @Param("minRating") BigDecimal minRating,
            @Param("userLat") Double userLat,
            @Param("userLng") Double userLng,
            @Param("maxDistance") Double maxDistance,
            Pageable pageable
    );
}