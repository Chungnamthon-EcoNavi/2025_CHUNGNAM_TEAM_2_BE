package com.example.econavi.member.service;

import com.example.econavi.auth.security.JwtBlacklistService;
import com.example.econavi.auth.service.AuthService;
import com.example.econavi.common.code.AuthResponseCode;
import com.example.econavi.common.exception.ApiException;
import com.example.econavi.member.dto.MemberDto;
import com.example.econavi.member.dto.MemberPhotoDto;
import com.example.econavi.member.dto.UpdateNameRequestDto;
import com.example.econavi.member.dto.UpdatePasswordRequestDto;
import com.example.econavi.member.entity.Member;
import com.example.econavi.member.entity.MemberPhoto;
import com.example.econavi.member.repository.MemberPhotoRepository;
import com.example.econavi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberPhotoRepository memberPhotoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtBlacklistService jwtBlacklistService;
    private final AuthService authService;

    @Transactional(readOnly = true)
    @PreAuthorize("@memberAccessHandler.ownershipCheck(#memberId)")
    public MemberDto getMemberDetailById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(AuthResponseCode.MEMBER_NOT_FOUND));

        List<MemberPhoto> photos = memberPhotoRepository.findByMember(member);

        return MemberDto.fromEntity(member, photos.stream().map(MemberPhotoDto::fromEntity).toList());
    }

    @Transactional
    @PreAuthorize("@memberAccessHandler.ownershipCheck(#memberId)")
    public MemberDto updateMemberName(
            Long memberId, UpdateNameRequestDto request
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(AuthResponseCode.MEMBER_NOT_FOUND));

        member.setName(request.getName());
        member = memberRepository.save(member);


        List<MemberPhoto> photos = memberPhotoRepository.findByMember(member);

        return MemberDto.fromEntity(member, photos.stream().map(MemberPhotoDto::fromEntity).toList());
    }

    @Transactional
    @PreAuthorize("@memberAccessHandler.ownershipCheck(#memberId)")
    public MemberDto updateMemberPassword(
            Long memberId, UpdatePasswordRequestDto request, String token
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(AuthResponseCode.MEMBER_NOT_FOUND));

        member.setPassword(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(member);

        authService.logout(memberId, token);

        List<MemberPhoto> photos = memberPhotoRepository.findByMember(member);

        return MemberDto.fromEntity(member, photos.stream().map(MemberPhotoDto::fromEntity).toList());
    }

    @Transactional
    @PreAuthorize("@memberAccessHandler.ownershipCheck(#memberId)")
    public MemberDto deleteMember(String token, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(AuthResponseCode.MEMBER_NOT_FOUND));
        memberRepository.deleteById(memberId);

        jwtBlacklistService.withdrawToken(token);
        SecurityContextHolder.clearContext();

        List<MemberPhoto> photos = memberPhotoRepository.findByMember(member);
        memberPhotoRepository.deleteAll(photos);

        return MemberDto.fromEntity(member, photos.stream().map(MemberPhotoDto::fromEntity).toList());
    }
}
