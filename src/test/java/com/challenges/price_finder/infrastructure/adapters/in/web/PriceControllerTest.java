package com.challenges.price_finder.infrastructure.adapters.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.challenges.price_finder.application.dto.PriceResponseDTO;
import com.challenges.price_finder.application.ports.in.FindApplicablePriceUseCase;
import com.challenges.price_finder.domain.exception.PriceNotFoundException;
import com.challenges.price_finder.domain.model.Price;
import com.challenges.price_finder.infrastructure.adapters.mapper.PriceMapper;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindApplicablePriceUseCase priceService;

    @MockBean
    private PriceMapper priceMapper;

    @Test
    void getApplicablePrice_ValidInput_ReturnsOk() throws Exception {
        Price samplePrice = new Price(
            1L,
            1L,
            LocalDateTime.of(2020, 6, 14, 10, 0),
            LocalDateTime.of(2020, 6, 14, 10, 0),
            1L,
            1L,
            1,
            BigDecimal.valueOf(1.0),
            "EUR"
        );
        PriceResponseDTO samplePriceResponseDTO = new PriceResponseDTO(
            1L,
            1L,
            1L,
            LocalDateTime.of(2020, 6, 14, 10, 0),
            LocalDateTime.of(2020, 6, 14, 10, 0),
            BigDecimal.valueOf(1.0),
            "EUR"
        );

        when(priceService.findApplicablePrice(anyLong(), anyLong(), any(LocalDateTime.class)))
            .thenReturn(samplePrice);
        when(priceMapper.priceToPriceResponseDTO(any(Price.class)))
            .thenReturn(samplePriceResponseDTO);

        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "2020-06-14T10:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brandId").value(samplePriceResponseDTO.getBrandId()))
                .andExpect(jsonPath("$.productId").value(samplePriceResponseDTO.getProductId()))
                .andExpect(jsonPath("$.priceList").value(samplePriceResponseDTO.getPriceList()))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.price").value(samplePriceResponseDTO.getPrice()));
    }

    @Test
    void getApplicablePrice_PriceNotFound_ReturnsNotFound() throws Exception {
        when(priceService.findApplicablePrice(anyLong(), anyLong(), any(LocalDateTime.class)))
            .thenThrow(new PriceNotFoundException("Price not found"));

        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "2020-06-14T10:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getApplicablePrice_InvalidBrandId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("brandId", "0")
                .param("productId", "35455")
                .param("applicationDate", "2020-06-14T10:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getApplicablePrice_InvalidProductId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "0")
                .param("applicationDate", "2020-06-14T10:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getApplicablePrice_InvalidDateFormat_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "2020-06-14T25:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getApplicablePrice_MissingParameter_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getApplicablePrice_InternalServerError_Returns500() throws Exception {
        when(priceService.findApplicablePrice(anyLong(), anyLong(), any(LocalDateTime.class)))
            .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "2020-06-14T10:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    
}
