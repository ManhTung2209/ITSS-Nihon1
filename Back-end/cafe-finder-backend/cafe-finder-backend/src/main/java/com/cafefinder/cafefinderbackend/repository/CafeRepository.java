package com.cafefinder.cafefinderbackend.repository;

import com.cafefinder.cafefinderbackend.model.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Integer> {

    Page<Cafe> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Cafe> findAll(Pageable pageable);

}