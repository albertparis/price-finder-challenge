package com.challenges.price_finder.infrastructure.adapters.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PriceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void findApplicablePrice_shouldReturnCorrectPrice() {
        // Given
        PriceJpaEntity price1 = createPrice(1L, 1L, LocalDateTime.of(2020, 6, 14, 0, 0), 
                                            LocalDateTime.of(2020, 12, 31, 23, 59), 1, new BigDecimal("35.50"));
        PriceJpaEntity price2 = createPrice(1L, 1L, LocalDateTime.of(2020, 6, 14, 15, 0), 
                                            LocalDateTime.of(2020, 6, 14, 18, 30), 2, new BigDecimal("25.45"));
        
        entityManager.persist(price1);
        entityManager.persist(price2);
        entityManager.flush();

        // When
        Optional<PriceJpaEntity> result = priceRepository.findApplicablePrice(1L, 1L, 
                                                          LocalDateTime.of(2020, 6, 14, 16, 0));

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getPrice()).isEqualTo(new BigDecimal("25.45"));
    }

    private PriceJpaEntity createPrice(Long brandId, Long productId, LocalDateTime startDate, 
                                       LocalDateTime endDate, int priority, BigDecimal price) {
        PriceJpaEntity priceEntity = new PriceJpaEntity();
        priceEntity.setBrandId(brandId);
        priceEntity.setProductId(productId);
        priceEntity.setStartDate(startDate);
        priceEntity.setEndDate(endDate);
        priceEntity.setPriority(priority);
        priceEntity.setPrice(price);
        return priceEntity;
    }
}