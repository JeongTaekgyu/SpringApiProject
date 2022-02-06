package com.taek.springapitest.service;

import com.taek.springapitest.dto.RestaurantDto;
import com.taek.springapitest.model.Restaurant;
import com.taek.springapitest.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// @RequiredArgsConstructor 는 초기화 되지않은 `final` 필드나, `@NonNull` 이 붙은 필드에 대해 생성자를 생성해 준다.
// 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용되곤 한다.
@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant registerRestaurant(RestaurantDto restaurantDto){
        Restaurant restaurant = new Restaurant(restaurantDto);

        if(restaurantDto.getMinOrderPrice() < 1000
                || restaurantDto.getMinOrderPrice() > 100000){
            throw new IllegalArgumentException("최소주문 허용가격이 아닙니다.");
        }

        if(restaurantDto.getMinOrderPrice() % 100 != 0){
            throw new IllegalArgumentException("100원단위 입력값이 아닙니다.");
        }

        if(restaurantDto.getDeliveryFee() <0 || restaurantDto.getDeliveryFee() > 10000){
            throw new IllegalArgumentException("기본배달비 허용 값이 아닙니다.");
        }

        if(restaurantDto.getDeliveryFee() % 500 != 0){
            throw new IllegalArgumentException("500원 단위 입력값이 아닙니다.");
        }

        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public List<Restaurant> getRestaurantList(){
        return restaurantRepository.findAll();
    }
}
