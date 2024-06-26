package org.asymptotes.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.asymptotes.ecommerce.customer.Address;


public record CustomerRequest(

        String id,
        @NotNull(message = "Customer firstname is required")
        String firstName,
        @NotNull(message = "Customer lastname is required")
        String lastName,
        @Email(message = "Customer email is not valid email ")
        String email,
        Address address
        ) {

}
