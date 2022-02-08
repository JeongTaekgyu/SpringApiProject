package com.taek.springapitest.controller;

import com.taek.springapitest.dto.OrderDto;
import com.taek.springapitest.dto.OrderRequestDto;
import com.taek.springapitest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/request")
    public OrderDto registerOrders(@RequestBody OrderRequestDto orderRequestDto){
        // @RequestBody List<OrderRequestDto> orderRequestDto 로 안하는 이유는
        // RequestBody 보면 전체로 리스트가 묶인게 아니라 아래와 같이 전송함
        /* 그러므로 Service로 가서 foods에 관한 list를 만들어야한다.
        {
          restaurantId: 1
          foods: [
            { id: 1, quantity: 1 },
            { id: 2, quantity: 2 },
            { id: 3, quantity: 3 }
          ]
        }
         */
        return orderService.registerOrders(orderRequestDto);
    }
}
