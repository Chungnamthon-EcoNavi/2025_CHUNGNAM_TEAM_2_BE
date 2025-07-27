package com.example.econavi.repository;

import com.example.econavi.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlaceId(Long placeId);

}
