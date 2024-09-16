package com.challenges.price_finder.infrastructure.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import com.challenges.price_finder.application.ports.out.LoadPricePort;
import com.challenges.price_finder.common.PersistenceAdapter;
import com.challenges.price_finder.domain.model.Price;
import com.challenges.price_finder.infrastructure.adapters.out.mapper.PriceJpaMapper;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PricePersistenceAdapter implements LoadPricePort{

    private final PriceRepository priceRepository;
    private final PriceJpaMapper priceJpaMapper;
    
    @Override
    public Optional<Price> findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        Optional<PriceJpaEntity> entities = priceRepository.findApplicablePrice(brandId, productId, applicationDate);
        return entities.map(priceJpaMapper::toDomainEntity);
    }
}
