package com.challenges.price_finder.application.ports.in;

import java.time.LocalDateTime;

import com.challenges.price_finder.domain.model.Price;

public interface FindApplicablePriceUseCase {
    Price findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
