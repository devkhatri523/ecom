package org.asymptotes.order.repository;

import ch.qos.logback.core.model.INamedModel;
import org.asymptotes.order.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
