package com.woof.api.orders.repository;



import com.woof.api.orders.model.Orders;
import com.woof.api.orders.model.dto.OrderDto;
import com.woof.api.orders.model.dto.OrdersMyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {


    public Optional<OrderDto> findByPhoneNumber(Long phoneNumber);
    List<Orders> findByMemberIdx(Long memberIdx);
}


