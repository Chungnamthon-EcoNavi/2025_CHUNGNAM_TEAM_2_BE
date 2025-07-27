package com.example.econavi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long placeId; // 어떤 장소의 리뷰인지

    @Column(nullable = false)
    private String content; // 리뷰 내용

    @Column(nullable = false)
    private String userId; // 작성자

    @Column(nullable = false)
    private int rating;
}
