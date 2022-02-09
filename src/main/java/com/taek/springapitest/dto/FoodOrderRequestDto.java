package com.taek.springapitest.dto;

import com.taek.springapitest.model.Food;
import com.taek.springapitest.model.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FoodOrderRequestDto {
    private Long id; // foodid
    private int quantity;
    
    // 여기서 name을 못넣어주네
    // 그래서 toEntity 사용 안함
}
