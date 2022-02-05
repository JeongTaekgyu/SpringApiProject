package com.taek.springapitest.dto;

import lombok.Getter;

@Getter
public class FoodDto {
    private Long restaurantId;
    private String name;
    private Long price;
    private Long quantity;
}
