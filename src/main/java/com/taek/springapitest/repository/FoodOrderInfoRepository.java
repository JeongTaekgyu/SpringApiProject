package com.taek.springapitest.repository;

import com.taek.springapitest.model.FoodOrdersInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderInfoRepository extends JpaRepository<FoodOrdersInfo, Long>{
}
