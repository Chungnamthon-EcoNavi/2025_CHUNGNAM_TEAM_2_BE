package com.example.econavi.member.repository;

import com.example.econavi.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    int countByUsername(String username);

    int countByNameAndIdNot(String name, Long memberId);
}