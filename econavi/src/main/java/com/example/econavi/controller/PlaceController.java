package com.example.econavi.controller;

import com.example.econavi.domain.Place;
import com.example.econavi.domain.User;
import com.example.econavi.repository.UserRepository;
import com.example.econavi.service.PlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;
    private final UserRepository userRepository;

    // 생성자 주입
    public PlaceController(PlaceService placeService, UserRepository userRepository) {
        this.placeService = placeService;
        this.userRepository = userRepository;
    }

    // 전체 조회
    @GetMapping
    public List<Place> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    // 단일 조회
    @GetMapping("/{id}")
    public Place getPlaceById(@PathVariable Long id) {
        return placeService.getPlaceById(id)
                .orElseThrow(() -> new RuntimeException("Place not found: " + id));
    }

    // 생성
    @PostMapping
    public Place createPlace(@RequestParam Long userId, @RequestBody Place place) {
        User loginUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        if (!"BUSINESS".equals(loginUser.getUserType())) {
            throw new RuntimeException("사업자만 등록 가능합니다.");
        }
        return placeService.savePlace(place);
    }

    // 수정
    @PutMapping("/{id}")
    public Place updatePlace(@RequestParam Long userId, @PathVariable Long id, @RequestBody Place place) {
        User loginUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        if (!"BUSINESS".equals(loginUser.getUserType())) {
            throw new RuntimeException("사업자만 수정 가능합니다.");
        }
        place.setId(id);
        return placeService.savePlace(place);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deletePlace(@RequestParam Long userId, @PathVariable Long id) {
        User loginUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        if (!"BUSINESS".equals(loginUser.getUserType())) {
            throw new RuntimeException("사업자만 삭제 가능합니다.");
        }
        placeService.deletePlace(id);
    }

}
