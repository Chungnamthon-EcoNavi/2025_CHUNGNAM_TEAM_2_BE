package com.example.econavi.point.dto;

import com.example.econavi.member.entity.Member;
import com.example.econavi.point.entity.PointHistory;
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
public class PointHistoryDto {
    private Long id;
    private Long memberId;
    private Long amount;
    private String reason;
    private Timestamp createdAt;

    public static PointHistoryDto fromEntity(PointHistory pointHistory) {
        return PointHistoryDto.builder()
                .id(pointHistory.getId())
                .memberId(pointHistory.getMember().getId())
                .amount(pointHistory.getAmount())
                .reason(pointHistory.getReason())
                .createdAt(pointHistory.getCreatedAt())
                .build();
    }
}
