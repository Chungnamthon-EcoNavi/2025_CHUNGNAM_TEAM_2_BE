package com.example.econavi.member.repository;

import com.example.econavi.member.entity.Member;
import com.example.econavi.member.entity.MemberPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberPhotoRepository extends JpaRepository<MemberPhoto, Long> {
    List<MemberPhoto> findByMember(Member member);
}
