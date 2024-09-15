package com.challenges.price_finder.infrastructure.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.challenges.price_finder.domain.model.Price;
import com.challenges.price_finder.infrastructure.adapters.out.mapper.PriceJpaMapper;

public class PricePersistenceAdapterTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceJpaMapper priceJpaMapper;

    @InjectMocks
    private PricePersistenceAdapter pricePersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findApplicablePrice_WhenPriceExists_ReturnsPrice() {
        // Arrange
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        PriceJpaEntity priceJpaEntity = new PriceJpaEntity(); // Assume this is properly set up
        Price expectedPrice = new Price(); // Assume this is properly set up

        when(priceRepository.findApplicablePrice(brandId, productId, applicationDate))
            .thenReturn(Optional.of(priceJpaEntity));
        when(priceJpaMapper.toDomainEntity(priceJpaEntity)).thenReturn(expectedPrice);

        // Act
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(brandId, productId, applicationDate);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
        verify(priceRepository).findApplicablePrice(brandId, productId, applicationDate);
        verify(priceJpaMapper).toDomainEntity(priceJpaEntity);
    }

    @Test
    void findApplicablePrice_WhenPriceDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(priceRepository.findApplicablePrice(brandId, productId, applicationDate))
            .thenReturn(Optional.empty());

        // Act
        Optional<Price> result = pricePersistenceAdapter.findApplicablePrice(brandId, productId, applicationDate);

        // Assert
        assertFalse(result.isPresent());
        verify(priceRepository).findApplicablePrice(brandId, productId, applicationDate);
        verifyNoInteractions(priceJpaMapper);
    }

    @Test
    void findApplicablePrice_WhenRepositoryThrowsException_PropagatesException() {
        // Arrange
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(priceRepository.findApplicablePrice(brandId, productId, applicationDate))
            .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> 
            pricePersistenceAdapter.findApplicablePrice(brandId, productId, applicationDate));
        
        assertEquals("Database error", exception.getMessage());
        verify(priceRepository).findApplicablePrice(brandId, productId, applicationDate);
        verifyNoInteractions(priceJpaMapper);
    }

}