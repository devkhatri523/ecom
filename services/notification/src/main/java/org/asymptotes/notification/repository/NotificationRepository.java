package org.asymptotes.notification.repository;

import org.asymptotes.notification.entities.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
