package com.challenges.price_finder.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenges.price_finder.application.ports.out.LoadPricePort;
import com.challenges.price_finder.domain.exception.PriceNotFoundException;
import com.challenges.price_finder.domain.model.Price;
 
@ExtendWith(MockitoExtension.class)
public class FindApplicablePriceServiceTest {
    @Mock
    private LoadPricePort loadPricePort;

    @InjectMocks
    private FindApplicablePriceService findApplicablePriceService;

    private final LocalDateTime testDate = LocalDateTime.of(2023, 6, 14, 10, 0);

    @Test
    void findApplicablePrice_whenPriceExists_shouldReturnThatPrice() {
        Price expectedPrice = createPrice(1L, 1, new BigDecimal("35.50"));
        when(loadPricePort.findApplicablePrice(1L, 35455L, testDate))
                .thenReturn(Optional.of(expectedPrice));

        Price result = findApplicablePriceService.findApplicablePrice(1L, 35455L, testDate);

        assertEquals(expectedPrice, result);
        verify(loadPricePort).findApplicablePrice(1L, 35455L, testDate);
    }

    @Test
    void findApplicablePrice_whenNoPrices_shouldThrowException() {
        when(loadPricePort.findApplicablePrice(1L, 35455L, testDate))
                .thenReturn(Optional.empty());

        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () -> 
            findApplicablePriceService.findApplicablePrice(1L, 35455L, testDate)
        );

        assertEquals("No price found for the given brandId, productId and applicationDate", exception.getMessage());
        verify(loadPricePort).findApplicablePrice(1L, 35455L, testDate);
    }

    private Price createPrice(Long id, Integer priority, BigDecimal price) {
        Price priceObj = new Price();
        priceObj.setId(id);
        priceObj.setBrandId(1L);
        priceObj.setStartDate(testDate.minusDays(1));
        priceObj.setEndDate(testDate.plusDays(1));
        priceObj.setPriceList(1L);
        priceObj.setProductId(35455L);
        priceObj.setPriority(priority);
        priceObj.setPrice(price);
        priceObj.setCurrency("EUR");
        return priceObj;
    }
}