package com.taek.springapitest.dto;

import com.taek.springapitest.model.Food;
import com.taek.springapitest.model.Orders;
import com.taek.springapitest.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.Order;

import java.util.List;

@Setter
@Getter
public class OrderDto {
    private String restaurantName;
    private List<FoodOrderDto> foods;
    private int deliveryFee;
    private int totalPrice;

    public OrderDto(Orders orders, List<FoodOrderDto> foods){
        this.restaurantName = orders.getRestaurant().getName();
        this.foods = foods;
        this.deliveryFee = orders.getRestaurant().getDeliveryFee();
        this.totalPrice = orders.getTotalPrice();
    }
}
