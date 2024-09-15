package com.challenges.price_finder.infrastructure.adapters.out.mapper;

import org.mapstruct.Mapper;

import com.challenges.price_finder.domain.model.Price;
import com.challenges.price_finder.infrastructure.adapters.out.persistence.PriceJpaEntity;

@Mapper(componentModel = "spring")
public interface PriceJpaMapper {
Price toDomainEntity(PriceJpaEntity priceJpaEntity);
}
