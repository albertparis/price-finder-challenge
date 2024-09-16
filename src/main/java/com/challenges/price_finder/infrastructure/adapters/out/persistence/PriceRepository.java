package com.challenges.price_finder.infrastructure.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceJpaEntity, Long> {
    @Query("SELECT p FROM PriceJpaEntity p WHERE p.brandId = :brandId AND p.productId = :productId AND p.startDate <= :applicationDate AND p.endDate >= :applicationDate ORDER BY p.priority DESC LIMIT 1")
    Optional<PriceJpaEntity> findApplicablePrice(
        @Param("brandId") Long brandId, 
        @Param("productId") Long productId,
        @Param("applicationDate") LocalDateTime applicationDate);
}
