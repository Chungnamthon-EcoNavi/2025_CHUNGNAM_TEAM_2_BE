package com.example.econavi.controller;

import com.example.econavi.domain.Review;
import com.example.econavi.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 장소별 리뷰 조회
    @GetMapping("/place/{placeId}")
    public List<Review> getReviewsByPlace(@PathVariable Long placeId) {
        return reviewService.getReviewsByPlace(placeId);
    }

    // 리뷰 등록
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    // 리뷰 수정(작성자 본인만)
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review review, @RequestParam String userId) {
        return reviewService.updateReviewByUser(id, review, userId);
    }

    // 리뷰 삭제(작성자 본인만)
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id, @RequestParam String userId) {
        reviewService.deleteReviewByUser(id, userId);
    }
}
