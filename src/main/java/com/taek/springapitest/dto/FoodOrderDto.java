package com.taek.springapitest.dto;

import com.taek.springapitest.model.Food;
import com.taek.springapitest.model.FoodOrdersInfo;
import com.taek.springapitest.model.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodOrderDto {
    String name;
    int quantity;
    int price;

    public FoodOrderDto(FoodOrdersInfo foodOrdersInfos){
        this.name = foodOrdersInfos.getFood().getName();
        this.quantity = foodOrdersInfos.getQuantity();
        this.price = foodOrdersInfos.getPrice();
    }
    /*public FoodOrderDto toEntity(Restaurant restaurant){
        return FoodOrderDto.builder()
                //.restaurant(restaurant)
                .name(this.name)
                .quantity(this.quantity)
                .price(this.price)
                .build();
    }*/
}
