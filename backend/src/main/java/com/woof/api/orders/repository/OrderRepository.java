package com.woof.api.orders.repository;



import com.woof.api.orders.model.entity.OrderDto;
import com.woof.api.orders.model.entity.Orders;
import com.woof.api.product.model.entity.ProductManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {


    public Optional<OrderDto> findByPhoneNumber(Long phoneNumber);
    List<Orders> findByMemberIdx(Long memberIdx);
    public Orders findByIdx(Long idx);

}


