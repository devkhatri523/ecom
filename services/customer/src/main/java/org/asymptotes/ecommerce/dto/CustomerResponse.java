package org.asymptotes.ecommerce.dto;

import org.asymptotes.ecommerce.customer.Address;

public record CustomerResponse(String id,
                               String firstName,
                               String lastName,
                               String email,
                               Address address) {

}
