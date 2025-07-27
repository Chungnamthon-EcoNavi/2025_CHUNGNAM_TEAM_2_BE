package com.example.econavi.auth.dto;


import com.example.econavi.member.type.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDto {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,100}$", message = "계정은 이메일 형식이어야 합니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,16}$", message = "이름은 2~16자, 한글과 알파벳 또는 숫자만 허용됩니다.")
    private String name;

    @NotNull
    private Role role;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,128}$", message = "비밀번호는 8자 이상, 숫자와 알파벳이 포함되어야 합니다.")
    //최소 8자, 숫자와 알파벳
    private String password;
}
