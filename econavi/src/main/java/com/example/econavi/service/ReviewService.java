package com.example.econavi.service;

import com.example.econavi.domain.Review;
import com.example.econavi.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //리뷰 목로 조희
    public List<Review> getReviewsByPlace(Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }

    //리뷰 등록
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    //리뷰 삭제
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    //작성자 검증 후 리뷰 수정
    public Review updateReviewByUser(Long id, Review updatedReview, String userId) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        existing.setContent(updatedReview.getContent());
        existing.setRating(updatedReview.getRating());
        // 필요하다면 다른 필드도 수정 가능
        return reviewRepository.save(existing);
    }

    //작성자 검증 후 리뷰 삭제
    public void deleteReviewByUser(Long id, String userId) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUserId().equals(userId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        reviewRepository.deleteById(id);
    }
}
