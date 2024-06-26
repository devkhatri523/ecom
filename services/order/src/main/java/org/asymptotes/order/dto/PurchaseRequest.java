package org.asymptotes.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product is mandaroty")
        Integer productId,
        @Positive(message = "Quantity is mandatory")
        double quantity
) {
}
