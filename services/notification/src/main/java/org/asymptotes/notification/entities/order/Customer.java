package org.asymptotes.notification.entities.order;

public record Customer(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
