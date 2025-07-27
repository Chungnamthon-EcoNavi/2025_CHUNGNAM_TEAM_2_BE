package com.example.econavi.auth.service;

import com.example.econavi.auth.dto.LoginDto;
import com.example.econavi.auth.dto.SignUpRequestDto;
import com.example.econavi.auth.security.JwtBlacklistService;
import com.example.econavi.auth.security.JwtUtil;
import com.example.econavi.common.exception.ApiException;
import com.example.econavi.member.entity.entity.Member;
import com.example.econavi.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final JwtBlacklistService jwtBlacklistService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginDto.Response login(LoginDto.Request request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Member member = (Member) auth.getPrincipal();

        return LoginDto.Response.builder()
                .memberId(member.getId().toString())
                .token(jwtUtil.generateToken(member.getId()))
                .build();
    }

    @Transactional
    public String signUp(@Valid SignUpRequestDto request) {
        if (memberRepository.countByUsername(request.getUsername()) > 0) {
            throw new ApiException(HttpStatus.CONFLICT, "중복된 계정입니다.");
        }

        Member member = Member.builder()
                .username(request.getUsername())
                .name(request.getName())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return memberRepository.save(member).getUsername();
    }

    public void logout(Long memberId, String token) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "사용자가 존재하지 않습니다."));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false); //인증 Context 초기화

        jwtBlacklistService.logoutToken(token); //Redis 블랙리스트에 토큰 추가
    }
}