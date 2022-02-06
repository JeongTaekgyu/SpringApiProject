package com.taek.springapitest.dto;

import lombok.Getter;

@Getter
public class FoodOrderRequestDto {
    private Long ordersId;
    private Long restaurantId;
    private Long quantity;
    private Long price;
}
