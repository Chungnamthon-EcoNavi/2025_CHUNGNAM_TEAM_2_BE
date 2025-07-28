package com.example.econavi.point.controller;

import com.example.econavi.auth.security.JwtUtil;
import com.example.econavi.point.dto.PointDto;
import com.example.econavi.point.dto.PointHistoryDto;
import com.example.econavi.point.service.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
@Tag(name = "EcoPoint 관련 서비스", description = "포인트 CRUD")
public class PointController {
    private final PointService pointService;
    private final JwtUtil jwtUtil;

    @GetMapping("")
    public ResponseEntity<PointDto> getPoint(
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").substring(7);
        Long memberId = jwtUtil.getIdFromToken(token);

        return ResponseEntity.ok(pointService.getPoint(memberId));
    }

    @GetMapping("/histories")
    public ResponseEntity<List<PointHistoryDto>> getPointHistories(
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").substring(7);
        Long memberId = jwtUtil.getIdFromToken(token);

        return ResponseEntity.ok(pointService.getPointHistories(memberId));
    }
}
