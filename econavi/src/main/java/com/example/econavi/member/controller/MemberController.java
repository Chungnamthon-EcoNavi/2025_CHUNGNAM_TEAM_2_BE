package com.example.econavi.member.controller;

import com.example.econavi.member.dto.MemberDto;
import com.example.econavi.member.dto.UpdateNameRequestDto;
import com.example.econavi.member.dto.UpdatePasswordRequestDto;
import com.example.econavi.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "사용자 관련 서비스", description = "로그인, 회원가입 등")
public class MemberController {
    private final MemberService memberService;


    @Operation(
            summary = "사용자 정보 조회",
            description = "식별자를 통한 사용자 정보 조희",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/detail")
    public ResponseEntity<MemberDto> getMemberDetail(
            @RequestParam @Valid Long member_id
    ) {
        return ResponseEntity.ok(memberService.getMemberDetailById(member_id));
    }

    @Operation(
            summary = "닉네임 변경",
            description = "닉네임 변경, 중복체크 있음",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/nckchn/{memberId}")
    public ResponseEntity<MemberDto> updateMemberNickname(
            @PathVariable Long memberId,
            @RequestBody @Valid UpdateNameRequestDto request
    ) {
        return ResponseEntity.ok(memberService.updateMemberName(memberId, request));
    }

    @Operation(
            summary = "비밀번호 변경",
            description = "비밀번호 변경, 8자 이상/알파벳+숫자+특수문자",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/pschn/{memberId}")
    public ResponseEntity<MemberDto> updateMemberPassword(
            @PathVariable Long memberId,
            @RequestBody @Valid UpdatePasswordRequestDto request,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);

        return ResponseEntity.ok(memberService.updateMemberPassword(memberId, request, token));
    }

    @Operation(
            summary = "회원 삭제",
            description = "테스트 용으로 실 사용 X",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<MemberDto> deleteMember(
            @PathVariable Long memberId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);

        return ResponseEntity.ok(memberService.deleteMember(token, memberId));
    }
}
