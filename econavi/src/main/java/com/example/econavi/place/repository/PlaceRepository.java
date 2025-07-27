package com.example.econavi.place.repository;

import com.example.econavi.place.entity.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByIdGreaterThanOrderByIdAsc(long cursor, Pageable pageable);

    int countByNameAndAddress(String name, String address);

    long countByNameAndAddressAndIdNot(String name, String address, Long id);
}
