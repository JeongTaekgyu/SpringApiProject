package com.taek.springapitest.dto;

import lombok.Getter;

@Getter
public class OrderDto {
    private Long restaurantId;
    private Long totalPrice;
    private Long foodId;
}
