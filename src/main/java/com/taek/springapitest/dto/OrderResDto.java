package com.taek.springapitest.dto;

import com.taek.springapitest.model.Orders;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderResDto {
    private String restaurantName;
    private List<FoodOrderResDto> foods;
    private int deliveryFee;
    private int totalPrice;

    public OrderResDto(Orders orders, List<FoodOrderResDto> foods){
        this.restaurantName = orders.getRestaurant().getName();
        this.foods = foods;
        this.deliveryFee = orders.getRestaurant().getDeliveryFee();
        this.totalPrice = orders.getTotalPrice();
    }
}
