package com.example.econavi.repository;

import com.example.econavi.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserId(String userId);
    void deleteByPlaceIdAndUserId(Long placeId, String userId);
}
