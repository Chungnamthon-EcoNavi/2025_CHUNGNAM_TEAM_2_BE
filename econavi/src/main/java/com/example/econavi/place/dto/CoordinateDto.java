package com.example.econavi.place.dto;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoordinateDto {
    @Digits(integer = 3, fraction = 7)
    private BigDecimal longitude;

    @Digits(integer = 3, fraction = 7)
    private BigDecimal latitude;
}
