package com.example.econavi.service;

import com.example.econavi.domain.Place;
import com.example.econavi.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    // 생성자 주입
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    // 전체 조회
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    // 단일 조회
    public Optional<Place> getPlaceById(Long id) {
        return placeRepository.findById(id);
    }

    // 생성 & 수정 (save는 id가 없으면 insert, 있으면 update)
    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    // 삭제
    public void deletePlace(Long id) {
        placeRepository.deleteById(id);
    }
}
