package com.taek.springapitest.dto;

import com.taek.springapitest.model.FoodOrdersInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodOrderResDto {
    String name;
    int quantity;
    int price;

    public FoodOrderResDto(FoodOrdersInfo foodOrdersInfos){
        this.name = foodOrdersInfos.getFood().getName();
        this.quantity = foodOrdersInfos.getQuantity();
        this.price = foodOrdersInfos.getPrice();
    }

}
