package org.asymptotes.order.dto;



public record OrderLineRequest(Integer Id,
                              Integer orderId,
                               Integer productId,
                               double quantity) {
}
