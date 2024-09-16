package com.challenges.price_finder.infrastructure.adapters.mapper;

import org.mapstruct.Mapper;

import com.challenges.price_finder.application.dto.PriceResponseDTO;
import com.challenges.price_finder.domain.model.Price;


@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceResponseDTO priceToPriceResponseDTO(Price price);
}
