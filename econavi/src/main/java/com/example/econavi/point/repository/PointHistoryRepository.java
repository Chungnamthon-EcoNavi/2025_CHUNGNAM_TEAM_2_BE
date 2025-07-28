package com.example.econavi.point.repository;

import com.example.econavi.member.entity.Member;
import com.example.econavi.point.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findAllByMember(Member member);
}
