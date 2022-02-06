package com.taek.springapitest.controller;

import com.taek.springapitest.dto.RestaurantDto;
import com.taek.springapitest.model.Restaurant;
import com.taek.springapitest.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RequiredArgsConstructor 는 초기화 되지않은 `final` 필드나, `@NonNull` 이 붙은 필드에 대해 생성자를 생성해 준다.
// 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용되곤 한다.
@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant/register")
    public Restaurant registerRestaurant(@RequestBody RestaurantDto restaurantDto){
        return restaurantService.registerRestaurant(restaurantDto);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurantInfo(){
        return restaurantService.getRestaurantList();
    }
}
