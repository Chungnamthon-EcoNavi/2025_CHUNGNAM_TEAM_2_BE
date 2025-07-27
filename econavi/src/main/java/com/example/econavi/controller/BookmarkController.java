package com.example.econavi.controller;

import com.example.econavi.domain.Bookmark;
import com.example.econavi.service.BookmarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    // 내 찜 목록
    @GetMapping
    public List<Bookmark> getBookmarks(@RequestParam String userId) {
        return bookmarkService.getBookmarksByUser(userId);
    }

    // 찜하기
    @PostMapping("/{placeId}")
    public Bookmark addBookmark(@PathVariable Long placeId, @RequestParam String userId) {
        Bookmark bookmark = new Bookmark();
        bookmark.setPlaceId(placeId);
        bookmark.setUserId(userId);
        return bookmarkService.saveBookmark(bookmark);
    }

    // 찜 해제
    @DeleteMapping("/{placeId}")
    public void removeBookmark(@PathVariable Long placeId, @RequestParam String userId) {
        bookmarkService.deleteBookmarkByUser(placeId, userId);
    }
}
