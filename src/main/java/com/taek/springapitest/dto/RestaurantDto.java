package com.taek.springapitest.dto;

import lombok.Getter;

@Getter
public class RestaurantDto {
    private String name;
    private Long minOrderPrice;
    private Long deliverFee;
}
