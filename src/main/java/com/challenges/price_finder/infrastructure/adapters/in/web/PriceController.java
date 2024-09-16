package com.challenges.price_finder.infrastructure.adapters.in.web;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenges.price_finder.application.dto.ErrorResponseDTO;
import com.challenges.price_finder.application.dto.PriceResponseDTO;
import com.challenges.price_finder.application.ports.in.FindApplicablePriceUseCase;
import com.challenges.price_finder.domain.model.Price;
import com.challenges.price_finder.infrastructure.adapters.mapper.PriceMapper;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/prices")
public class PriceController {

    private final FindApplicablePriceUseCase priceService;
    private final PriceMapper priceMapper;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Price found", content = @Content(schema = @Schema(implementation = PriceResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Price not found", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<PriceResponseDTO> getApplicablePrice(
            @RequestParam @NotNull @Min(1) Long brandId,
            @RequestParam @NotNull @Min(1) Long productId,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        
        Price price = priceService.findApplicablePrice(brandId, productId, applicationDate);
        return ResponseEntity.ok(priceMapper.priceToPriceResponseDTO(price));
    }
}
