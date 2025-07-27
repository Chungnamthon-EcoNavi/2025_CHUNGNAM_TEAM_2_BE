package com.example.econavi.place.controller;

import com.example.econavi.auth.security.JwtUtil;
import com.example.econavi.place.dto.AddPlaceRequestDto;
import com.example.econavi.place.dto.PlaceDto;
import com.example.econavi.place.service.PlaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
@Tag(name = "장소 관련 서비스", description = "장소 CRUD")
public class PlaceController {
    private final PlaceService placeService;
    private final JwtUtil jwtUtil;

    @GetMapping("")
    public ResponseEntity<List<PlaceDto>> getPlaces(
            @RequestParam(required = false) Long last_id,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(placeService.getPlaces(last_id, size));
    }

    @PostMapping("/add")
    public ResponseEntity<PlaceDto> addPlace(
            @Valid @RequestBody AddPlaceRequestDto request,
            HttpServletRequest httpServletRequest
    ) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        Long memberId = jwtUtil.getIdFromToken(token);

        return ResponseEntity.ok(placeService.addPlace(memberId, request));
    }

    @PatchMapping("/update/{placeId}")
    public ResponseEntity<PlaceDto> updatePlace(
            @PathVariable Long placeId,
            @Valid @RequestBody AddPlaceRequestDto request
    ) {
        return ResponseEntity.ok(placeService.updatePlace(placeId, request));
    }

    @DeleteMapping("/delete/{placeId}")
    public ResponseEntity<PlaceDto> deletePlace(
            @PathVariable Long placeId
    ) {
        return ResponseEntity.ok(placeService.deletePlace(placeId));
    }
}
