package com.example.econavi.service;

import com.example.econavi.domain.Bookmark;
import com.example.econavi.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    //특정 사용자의 북마크 목록 조희
    public List<Bookmark> getBookmarksByUser(String userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    //북마크 저장
    public Bookmark saveBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    //북마크id로만 삭제
    public void deleteBookmark(Long bookmarkId) {

        bookmarkRepository.deleteById(bookmarkId);
    }

    //사용자 검증 후 삭제
    public void deleteBookmarkByUser(Long bookmarkId, String userId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new RuntimeException("Bookmark not found"));

        // 북마크의 작성자(userId)가 맞는지 체크
        if (!bookmark.getUserId().equals(userId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        bookmarkRepository.deleteById(bookmarkId);
    }

    // placeId + userId 조합으로 삭제가 필요하다면 아래 메서드도 사용 가능
    public void deleteBookmarkByPlaceAndUser(Long placeId, String userId) {
    }
}
