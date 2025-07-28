package com.example.econavi.place.service;

import com.example.econavi.common.code.AuthResponseCode;
import com.example.econavi.common.code.GeneralResponseCode;
import com.example.econavi.common.exception.ApiException;
import com.example.econavi.common.service.FileStorageService;
import com.example.econavi.member.entity.Member;
import com.example.econavi.member.entity.MemberPhoto;
import com.example.econavi.member.repository.MemberRepository;
import com.example.econavi.member.type.Role;
import com.example.econavi.place.dto.AddPlaceRequestDto;
import com.example.econavi.place.dto.CoordinateDto;
import com.example.econavi.place.dto.PlaceDto;
import com.example.econavi.place.dto.PlacePhotoDto;
import com.example.econavi.place.entity.Place;
import com.example.econavi.place.entity.PlacePhoto;
import com.example.econavi.place.repository.PlacePhotoRepository;
import com.example.econavi.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final PlacePhotoRepository placePhotoRepository;
    private final FileStorageService fileStorageService;

    @Transactional(readOnly = true)
    public List<PlaceDto> getPlaces(Long lastId, int size) {
        long cursor = lastId == null ? 0L : lastId;
        int pageSize = (size <= 0) ? 10 : size;

        List<Place> places = placeRepository.findByIdGreaterThanOrderByIdAsc(
                cursor, PageRequest.of(0, pageSize));

        List<PlaceDto> placeDtos = new ArrayList<>();
        for (Place place : places) {
            List<PlacePhoto> placePhotos = placePhotoRepository.findByPlace(place);
            placeDtos.add(
                    PlaceDto.fromEntity(
                            place,
                            placePhotos.stream().map(PlacePhotoDto::fromEntity).toList()
                    )
            );
        }

        return placeDtos;
    }

    @Transactional(readOnly = true)
    public List<PlaceDto> searchPlaces(String keyword) {
        List<Place> places = placeRepository.findByNameContaining(keyword);

        List<PlaceDto> placeDtos = new ArrayList<>();
        for (Place place : places) {
            List<PlacePhoto> placePhotos = placePhotoRepository.findByPlace(place);
            placeDtos.add(
                    PlaceDto.fromEntity(
                            place,
                            placePhotos.stream().map(PlacePhotoDto::fromEntity).toList()
                    )
            );
        }

        return placeDtos;
    }

    @Transactional(readOnly = true)
    public List<PlaceDto> getAroundPlaces(CoordinateDto currentCoordinate, double distanceInKm) {
        List<Place> places = placeRepository.findPlaceWithDistance(
                currentCoordinate.getLatitude(),
                currentCoordinate.getLongitude(),
                distanceInKm
        );

        List<PlaceDto> placeDtos = new ArrayList<>();
        for (Place place : places) {
            List<PlacePhoto> placePhotos = placePhotoRepository.findByPlace(place);
            placeDtos.add(
                    PlaceDto.fromEntity(
                            place,
                            placePhotos.stream().map(PlacePhotoDto::fromEntity).toList()
                    )
            );
        }

        return placeDtos;
    }

    @Transactional
    @PreAuthorize("@memberAccessHandler.ownershipCheck(#memberId)")
    public PlaceDto addPlace(Long memberId, AddPlaceRequestDto request, List<MultipartFile> images) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(AuthResponseCode.MEMBER_NOT_FOUND));

        if (member.getRole() != Role.STAFF) {
            throw new ApiException(AuthResponseCode.UNAUTHORIZED);
        }

        int count = placeRepository.countByNameAndAddress(request.getName(), request.getAddress());

        if (count > 0) {
            throw new ApiException(GeneralResponseCode.DUPLICATED_PLACE);
        }

        Place place = Place.builder()
                .name(request.getName())
                .address(request.getAddress())
                .placeType(request.getPlaceType())
                .owner(member)
                .description(request.getDescription())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        place = placeRepository.save(place);

        List<PlacePhoto> placePhotos = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                try {
                    String url = fileStorageService.store(image);
                    PlacePhoto placePhoto = PlacePhoto.builder()
                            .photoUrl(url)
                            .place(place)
                            .build();
                    placePhotos.add(placePhoto);
                } catch (IOException e) {
                    throw new ApiException(GeneralResponseCode.INTERNAL_SERVER_ERROR);
                }
            }

            placePhotoRepository.saveAll(placePhotos);
        }

        return PlaceDto.fromEntity(place, placePhotos.stream().map(PlacePhotoDto::fromEntity).toList());
    }

    @Transactional
    @PreAuthorize("@placeAccessHandler.ownershipCheck(#placeId)")
    public PlaceDto updatePlace(Long placeId, AddPlaceRequestDto request) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ApiException(GeneralResponseCode.PLACE_NOT_FOUND));

        long count = placeRepository.countByNameAndAddressAndIdNot(request.getName(), request.getAddress(), placeId);

        if (count > 0) {
            throw new ApiException(GeneralResponseCode.DUPLICATED_PLACE);
        }

        place.setName(request.getName());
        place.setDescription(request.getDescription());
        place.setAddress(request.getAddress());
        place.setPlaceType(request.getPlaceType());
        place.setLatitude(request.getLatitude());
        place.setLongitude(request.getLongitude());
        place.setStartDate(request.getStartDate());
        place.setEndDate(request.getEndDate());
        place = placeRepository.save(place);

        List<PlacePhoto> placePhotos = placePhotoRepository.findByPlace(place);

        return PlaceDto.fromEntity(place, placePhotos.stream().map(PlacePhotoDto::fromEntity).toList());
    }

    @Transactional
    @PreAuthorize("@placeAccessHandler.ownershipCheck(#placeId)")
    public PlaceDto deletePlace(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ApiException(GeneralResponseCode.PLACE_NOT_FOUND));

        List<PlacePhoto> placePhoto = placePhotoRepository.findByPlace(place);

        placeRepository.delete(place);

        for (PlacePhoto photo : placePhoto) {
            fileStorageService.delete(photo.getPhotoUrl());
        }

        return PlaceDto.fromEntity(place, List.of());
    }
}
