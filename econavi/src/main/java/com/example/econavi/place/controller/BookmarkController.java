package com.example.econavi.place.controller;

import com.example.econavi.auth.security.JwtUtil;
import com.example.econavi.place.dto.BookmarkDto;
import com.example.econavi.place.dto.PlaceDto;
import com.example.econavi.place.service.BookmarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
@Tag(name = "찜 관련 서비스", description = "찜 CRUD")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final JwtUtil jwtUtil;

    @GetMapping("/all")
    public ResponseEntity<List<BookmarkDto>> getBookmarks(
            @RequestParam Long member_id
    ) {
        return ResponseEntity.ok(bookmarkService.getBookmarks(member_id));
    }

    @PostMapping("/add/{placeId}")
    public ResponseEntity<BookmarkDto> addBookmark(
            @PathVariable Long placeId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7); // "Bearer " 제거
        Long memberId = jwtUtil.getIdFromToken(token);

        return ResponseEntity.ok(bookmarkService.addBookmark(memberId, placeId));
    }

    @DeleteMapping("/delete/{bookmarkId}")
    public ResponseEntity<BookmarkDto> deleteBookmark(
            @PathVariable Long bookmarkId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7); // "Bearer " 제거
        Long memberId = jwtUtil.getIdFromToken(token);

        return ResponseEntity.ok(bookmarkService.deleteBookmark(memberId, bookmarkId));
    }
}
