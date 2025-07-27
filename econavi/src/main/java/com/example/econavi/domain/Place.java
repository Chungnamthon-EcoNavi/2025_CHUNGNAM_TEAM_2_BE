package com.example.econavi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity //
@Table(name = "places")
@Getter @Setter
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID

    @Column(nullable = false) // NOT NULL
    private String name; // 장소 이름

    @Column(nullable = false)
    private String address; // 주소

    @Column
    private String description; // 설명

    @Column
    private double latitude; // 위도

    @Column
    private double longitude; // 경도

    // 필요하면 생성자나 추가 메서드도 작성 가능
    public Place(String name, String address, String description, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
