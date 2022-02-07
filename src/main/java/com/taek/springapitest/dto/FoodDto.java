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

    private Long restaurantId;
    private String name;
    private int price;

    public Food toEntity(Restaurant restaurant){
        return Food.builder()
                .restaurant(restaurant)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
