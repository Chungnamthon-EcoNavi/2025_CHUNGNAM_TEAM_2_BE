package com.example.econavi.point.dto;

import com.example.econavi.member.entity.Member;
import com.example.econavi.point.entity.Point;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDto {
    private Long memberId;
    private Long amount;
    private Timestamp updatedAt;

    public static PointDto fromEntity(Point point) {
        return PointDto.builder()
                .memberId(point.getMemberId())
                .amount(point.getAmount())
                .updatedAt(point.getUpdatedAt())
                .build();
    }
}
