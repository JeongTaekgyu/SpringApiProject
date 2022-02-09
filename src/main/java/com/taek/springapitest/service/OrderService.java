package com.taek.springapitest.service;

import com.taek.springapitest.dto.*;
import com.taek.springapitest.model.Food;
import com.taek.springapitest.model.FoodOrdersInfo;
import com.taek.springapitest.model.Orders;
import com.taek.springapitest.model.Restaurant;
import com.taek.springapitest.repository.FoodOrderInfoRepository;
import com.taek.springapitest.repository.FoodRepository;
import com.taek.springapitest.repository.OrderRepository;
import com.taek.springapitest.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final FoodOrderInfoRepository foodOrderInfoRepository;

    @Transactional
    public OrderDto registerOrders(OrderRequestDto orderRequestDto){
        Restaurant restaurant = restaurantRepository.findById(orderRequestDto.getRestaurantId()).orElseThrow(
                () -> new NullPointerException("음식점 id가 존재하지 않음")
        );
        int totalPrice = restaurant.getDeliveryFee();   // totalPrice에 deliveryFee가 포함 되기 때문에

        List<FoodOrderRequestDto> foodOrderRequestDtoList = orderRequestDto.getFoods();
        List<FoodOrdersInfo> foodOrdersInfoList = new ArrayList<>();
        List<FoodOrderDto> foods = new ArrayList<>();

        for (FoodOrderRequestDto foodOrderRequestDto: foodOrderRequestDtoList) {
            if(foodOrderRequestDto.getQuantity() < 1 || foodOrderRequestDto.getQuantity() > 100){
                throw new IllegalArgumentException("음식을 주문 수량 허용값이 아닙니다.(허용값: 1 ~ 100)");
            }

            // foodId와 quantity는 FoodOrderRequestDto 에서 가져오고
            // price는 Food 에서 가져왔다.
            Long foodId = foodOrderRequestDto.getId();
            Food food = foodRepository.findById(foodId).orElseThrow(
                    () -> new NullPointerException("해당 음식 id가 없습니다.")
            );

            int foodQuantity = foodOrderRequestDto.getQuantity();
            int foodPriceByQuantity = food.getPrice() * foodQuantity;

            // 이거 foreach문이 아니라 stream 으로 하면 왜 안됐는지 의문이다.
            totalPrice = totalPrice + foodPriceByQuantity;

            // foodOrderInfo를 리스트화 시킨다음에 Orders 빼고 넣어준다.
            // toEntity를 쓰기에는 FoodOrderRequestDto랑 OrderRequestDto가 나눠져 있어서 안된다.
            // 그냥 여기서 builder를 쓰자
            FoodOrdersInfo foodOrdersInfo = FoodOrdersInfo.builder()
                    .quantity(foodQuantity)
                    .price(foodPriceByQuantity)
                    .food(food)
                    .build();

            // builder로 처리한걸 주문 상세정보 리스트에 넣어준다.
            foodOrdersInfoList.add(foodOrdersInfo);
        }

        if(totalPrice - restaurant.getDeliveryFee() < restaurant.getMinOrderPrice()){
            throw new IllegalArgumentException("음식점 최소주문 가격 미만입니다");
        }

        Orders orders = new Orders(restaurant, totalPrice);
        orderRepository.save(orders);

        // 주문 상세정보 리스트를 Orders 포함해서 넣어준다
        for (FoodOrdersInfo foodOrdersInfo: foodOrdersInfoList) {

            // orders를 포함한 정보를 다시 넣어준다.
            FoodOrdersInfo foodOrdersInfos = FoodOrdersInfo.builder()
                    .quantity(foodOrdersInfo.getQuantity())
                    .price(foodOrdersInfo.getPrice())
                    .food(foodOrdersInfo.getFood())
                    .orders(orders) // 위에서 만든 orders 정보를 FoodOrdersInfo에 있는 orders에 넣어준다.
                    .build();

            FoodOrderDto foodOrderDto = new FoodOrderDto(foodOrdersInfos);
            foods.add(foodOrderDto);

            foodOrderInfoRepository.save(foodOrdersInfos);
        }

        OrderDto orderDto = new OrderDto(orders, foods);

        return orderDto;
        //return orderRequestDto;
    }

    public List<OrderDto> getOrderList() {
        List<OrderDto> orderResponseDtoList = new ArrayList<>();

        List<Orders> ordersList = orderRepository.findAll();

        // OrderDto에 들어갈 파라미터중 하나인 foodOrdersInfoList 선언
        List<FoodOrderDto> foodOrderDtoList = new ArrayList<>();
        
        for(Orders orders : ordersList){
            // Orders를 기준으로 foodOrdersInfoList를 가져오기
            List<FoodOrdersInfo> foodOrdersInfoList = foodOrderInfoRepository.findAllByOrders(orders);
            for(FoodOrdersInfo foodOrdersInfo : foodOrdersInfoList){
                int quantity = foodOrdersInfo.getQuantity();
                int price = foodOrdersInfo.getPrice();
                Food food = foodOrdersInfo.getFood();

                FoodOrdersInfo foodOrdersInfos = FoodOrdersInfo.builder()
                        .quantity(foodOrdersInfo.getQuantity())
                        .price(foodOrdersInfo.getPrice())
                        .food(foodOrdersInfo.getFood())
                        .orders(orders) // 위에서 만든 orders 정보를 FoodOrdersInfo에 있는 orders에 넣어준다.
                        .build();

                FoodOrderDto foodOrderDto = new FoodOrderDto(foodOrdersInfos);
                foodOrderDtoList.add(foodOrderDto);
            }

            OrderDto orderDto = new OrderDto(orders, foodOrderDtoList);
            orderResponseDtoList.add(orderDto);
        }

        return orderResponseDtoList;
    }
}
