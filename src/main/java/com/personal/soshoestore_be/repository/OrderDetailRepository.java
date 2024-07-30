package com.personal.soshoestore_be.repository;

import com.personal.soshoestore_be.model.OrderDetail;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = :orderId")
    List<OrderDetail> findByOrderId(Long orderId);

    @Query("SELECT od FROM OrderDetail od WHERE od.id = :id")
    Optional<OrderDetail> findById(Long  id);
}
