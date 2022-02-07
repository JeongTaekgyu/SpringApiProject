package com.taek.springapitest.controller;

import com.taek.springapitest.dto.FoodDto;
import com.taek.springapitest.model.Food;
import com.taek.springapitest.model.Restaurant;
import com.taek.springapitest.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RequiredArgsConstructor 는 초기화 되지않은 `final` 필드나, `@NonNull` 이 붙은 필드에 대해 생성자를 생성해 준다.
// 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용되곤 한다.
@RequiredArgsConstructor
@RestController
public class FoodController {

    private final FoodService foodService;

    // 반환 타입이 ResponseEntity<Object>가 아니라 void 여도 통과한다.
    // assertNull 을 맞추려면 ResponseEntity<Object>으로 반환해라
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registerFoodAndRestaurantId(@PathVariable Long restaurantId, @RequestBody List<FoodDto> foodDto){
        foodService.registerFood(restaurantId, foodDto);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> getRestaurantFoodList(@PathVariable Long restaurantId){
        return foodService.getRestaurantFoodList(restaurantId);
    }
}
