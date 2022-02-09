package com.taek.springapitest.repository;

import com.taek.springapitest.model.FoodOrdersInfo;
import com.taek.springapitest.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderInfoRepository extends JpaRepository<FoodOrdersInfo, Long>{
    List<FoodOrdersInfo> findAllByOrders(Orders orders);
}
