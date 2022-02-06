package com.taek.springapitest.dto;

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

    private Long id;
    private String name;
    private int price;
}
