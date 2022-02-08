package com.taek.springapitest.dto;

import com.taek.springapitest.model.Food;
import com.taek.springapitest.model.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FoodDto {
    //private Long id;
    //private Long restaurantId;
    //private String name;

    private Long restaurantId;  // 그냥 id 로 해도 되지 않아?
    private String name;
    private int price;
    
    // 생성자가 아닌 builder를 사용함
    // 여기서 왜 .restaurant만 형식이 다르지?
    public Food toEntity(Restaurant restaurant){
        return Food.builder()
                .restaurant(restaurant)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
