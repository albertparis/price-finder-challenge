package com.challenges.price_finder.application.ports.out;

import java.time.LocalDateTime;
import java.util.Optional;

import com.challenges.price_finder.domain.model.Price;

public interface LoadPricePort {
Optional<Price> findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
