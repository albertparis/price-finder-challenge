package com.challenges.price_finder.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.challenges.price_finder.application.ports.in.FindApplicablePriceUseCase;
import com.challenges.price_finder.application.ports.out.LoadPricePort;
import com.challenges.price_finder.domain.exception.PriceNotFoundException;
import com.challenges.price_finder.domain.model.Price;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindApplicablePriceService implements FindApplicablePriceUseCase {
    
    private final LoadPricePort loadPricePort;

    @Override
    public Price findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return loadPricePort.findApplicablePrice(brandId, productId, applicationDate)
                .orElseThrow(() -> new PriceNotFoundException("No price found for the given brandId, productId and applicationDate"));
    }
}
