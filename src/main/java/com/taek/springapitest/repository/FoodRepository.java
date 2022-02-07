package com.taek.springapitest.repository;

import com.taek.springapitest.model.Food;
import com.taek.springapitest.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findFoodByRestaurantAndName(Restaurant restaurant, String foodName);

    List<Food> findAllByRestaurantId(Long restaurantId);
}
