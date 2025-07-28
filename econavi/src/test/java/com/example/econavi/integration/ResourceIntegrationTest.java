package com.example.econavi.integration;

import com.example.econavi.auth.dto.LoginDto;
import com.example.econavi.auth.dto.SignUpRequestDto;
import com.example.econavi.auth.security.JwtUtil;
import com.example.econavi.common.service.FileStorageService;
import com.example.econavi.member.dto.MemberDto;
import com.example.econavi.member.entity.Member;
import com.example.econavi.member.repository.MemberRepository;
import com.example.econavi.member.type.Role;
import com.example.econavi.place.dto.AddPlaceRequestDto;
import com.example.econavi.place.dto.PlaceDto;
import com.example.econavi.place.repository.PlaceRepository;
import com.example.econavi.place.type.PlaceType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResourceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JwtUtil jwtUtil;

    private List<String> files = new ArrayList<>();

    private AddPlaceRequestDto addPlaceRequestDto;
    private SignUpRequestDto signUpRequestDto;
    private String token;

    @BeforeEach
    void setUp() {
        signUpRequestDto = SignUpRequestDto.builder()
                .username("test@test.ccom")
                .role(Role.STAFF)
                .password("testtest123!")
                .name("test")
                .build();

        addPlaceRequestDto = AddPlaceRequestDto.builder()
                .name("test")
                .placeType(PlaceType.EVENT)
                .build();
    }

    @AfterEach
    void tearDown() {
        placeRepository.deleteAll();
        memberRepository.deleteAll();
        for (String filename : files) {
            fileStorageService.delete(filename);
        }
    }

    @Test
    @DisplayName("[FileController][Integration] getMemberPhoto test_success")
    void getMemberPhoto_test_success() throws Exception {
        String json = objectMapper.writeValueAsString(signUpRequestDto);

        mockMvc.perform(multipart("/auth/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        LoginDto.Request loginRequest = LoginDto.Request.builder()
                .username("test@test.ccom")
                .password("testtest123!")
                .build();

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        LoginDto.Response response =
                objectMapper.readValue(result.getResponse().getContentAsString(), LoginDto.Response.class);

        token = response.getToken();

        MvcResult resultMember = mockMvc.perform(get("/member/detail")
                        .param("member_id", response.getMemberId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        MemberDto memberDto = objectMapper.readValue(resultMember.getResponse().getContentAsString(), MemberDto.class);

        mockMvc.perform(get("/download")
                        .param("file_name", memberDto.getPhotoDtos().get(0).getPhotoUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        files.add(memberDto.getPhotoDtos().get(0).getPhotoUrl());
    }

    @Test
    @DisplayName("[FileController][Integration] getPlacePhoto test_success")
    void getPlacePhoto_test_success() throws Exception {
        Member member = memberRepository.save(Member.builder()
                .name("test")
                .role(Role.STAFF)
                .username("test@test.ccom")
                .password("testtest123!")
                .build());
        token = jwtUtil.generateToken(member.getId());
        String json = objectMapper.writeValueAsString(addPlaceRequestDto);

        MockMultipartFile requestFile = new MockMultipartFile(
                "request",
                "",
                "application/json",
                json.getBytes()
        );
        MockMultipartFile imageFile = new MockMultipartFile(
                "images",
                "image.jpg",
                "image/jpeg",
                "Fake content".getBytes()
        );

        MvcResult result = mockMvc.perform(multipart("/place/add")
                        .file(requestFile)
                        .file(imageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        PlaceDto placeDto = objectMapper.readValue(result.getResponse().getContentAsString(), PlaceDto.class);

        mockMvc.perform(get("/download")
                        .param("file_name", placeDto.getPhotoDtos().get(0).getPhotoUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        files.add(placeDto.getPhotoDtos().get(0).getPhotoUrl());
    }
}
