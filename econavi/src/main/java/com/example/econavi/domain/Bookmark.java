package com.example.econavi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookmarks")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long placeId; // 어떤 장소를 찜했는지

    @Column(nullable = false)
    private String userId; // 북마크 한 사용자 ID

}
