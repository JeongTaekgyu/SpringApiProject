package com.taek.springapitest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderRequestDto {
    //private Long restaurantId;
    //private int totalPrice;

    //private String restaurantName;
    private Long restaurantId;
    private List<FoodOrderRequestDto> foods;
//    private int deliveryFee;
//    private int totalPrice;
}
