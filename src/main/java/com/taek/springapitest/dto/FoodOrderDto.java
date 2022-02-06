package com.taek.springapitest.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodOrderDto {
    String name;
    int quantity;
    int price;
}
