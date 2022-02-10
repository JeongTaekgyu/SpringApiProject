package com.taek.springapitest.service;

import com.taek.springapitest.dto.FoodDto;
import com.taek.springapitest.dto.RestaurantDto;
import com.taek.springapitest.model.Food;
import com.taek.springapitest.model.Restaurant;
import com.taek.springapitest.repository.FoodRepository;
import com.taek.springapitest.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void registerFood(Long restaurantId, List<FoodDto> foodDtoList){

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NullPointerException("음식점 id가 존재하지 않음")
        );

        foodDtoList.stream().forEach(foodDto -> {

            if(foodDto.getPrice() < 100 || foodDto.getPrice() > 1000000 ){
                throw new IllegalArgumentException("음식 가격 허용값이 아닙니다.(허용값: 100원 ~ 1,000,000원)");
            }

            if(foodDto.getPrice() % 100 != 0){
                throw new IllegalArgumentException("100원 단위 입력이 아닙니다.");
            }

            // 이거 맞나? findFoodByRestaurantAndName
            Optional<Food> food = foodRepository.findFoodByRestaurantAndName(restaurant, foodDto.getName());
            if(food.isPresent()){
                throw new IllegalArgumentException("같은 음식점 내에서는 음식이름이 중복될 수 없습니다.");
            }

            foodDto.setRestaurantId(restaurantId); // ★
            foodRepository.save(foodDto.toEntity(restaurant));
        });

        //return food;
    }

    // jpa구문 2개이상 (논리 연산과정 2개이상은 @Transactional 을 해줘라 - 걍 1개 있어도 @Transactional 붙이자
    // 똑같은 id면 캐쉬에 담아져 있는거 그대로 가져와서 db안들린다 그러므로 @Transactional
    @Transactional
    public List<Food> getRestaurantFoodList(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NullPointerException("해당 음식점 id가 존재하지 않습니다.")
        );

        return foodRepository.findAllByRestaurantId(restaurant.getId());
    }

}
