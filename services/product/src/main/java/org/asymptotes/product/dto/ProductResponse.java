package org.asymptotes.product.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;

public record ProductResponse(
         Integer id,
         String name,
         String description,
         double availableQuantity,
         BigDecimal price,
         Integer categoryId,
         String categoryName,
         String categoryDescription
) {
}
