package org.asymptotes.order.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;

public record PurchaseResponse(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity

) {
}
