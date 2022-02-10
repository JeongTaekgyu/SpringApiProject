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
    public OrderResDto registerOrders(OrderRequestDto orderRequestDto){
        // orderRequestDto로 들어온 데이터중 restaurantId가 있는지 확인한다.
        Restaurant restaurant = restaurantRepository.findById(orderRequestDto.getRestaurantId()).orElseThrow(
                () -> new NullPointerException("음식점 id가 존재하지 않음")
        );
        int totalPrice = restaurant.getDeliveryFee();   // totalPrice에 deliveryFee가 포함 되기 때문에
        
        // 주문요청(OrderRequestDto 에 들어있던 음식리스트를 주문상세요청(FoodOrderRequestDto)에 넣어줌
        List<FoodOrderRequestDto> foodOrderRequestDtoList = orderRequestDto.getFoods();

        // 처음에는 FoodOrdersInfo 에 파라미터를 담을 때 Orders 파라미터는 못담기 때문에
        // 빌더를 이용해서 다른 파라미터들(quantity, price, Food food 을 먼저 담아주는 작업이 필요한데
        // 그 때 리스트를 담기위해서 선언한다.
        List<FoodOrdersInfo> foodOrdersInfoList = new ArrayList<>();

        // OrderResDto 의 인자로 들어가는 FoodOrderResDto 리스트
        List<FoodOrderResDto> foods = new ArrayList<>();

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
            // 리스트에 담는이유는 아래 반복문에서 활용하기 위해서다
        }

        if(totalPrice - restaurant.getDeliveryFee() < restaurant.getMinOrderPrice()){
            throw new IllegalArgumentException("음식점 최소주문 가격 미만입니다");
        }

        // totalPrice 때문에 Orders 객체를 여기서 선언해야 하는구나
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

            // 근데 궁금한게.. 그럼 new FoodOrderResDto(foodOrdersInfos) 이렇게 넣어줄때
            // foodOrdersInfos 에 orders 도 포함 돼있는데 
            // foods.add(foodOrderResDto) 할 때는 Order를 넣는 곳이 없다
            // 하지만 foodOrderInfoRepository 에서 저장해준다. 그러므로 orders도 넣어야한다.
            FoodOrderResDto foodOrderResDto = new FoodOrderResDto(foodOrdersInfos);
            // FoodOrderResDto foods 에 FoodOrdersInfo를 빌드한 데이터를 넣어준다.
            // foods는 OrderResDto의 인자로 사용될거다
            foods.add(foodOrderResDto);

            foodOrderInfoRepository.save(foodOrdersInfos);
        }

        OrderResDto orderResDto = new OrderResDto(orders, foods);

        return orderResDto;
    }


    // 주문 조회 - 그동안 성공한 모든 주문 요청을 조회
    public List<OrderResDto> getOrderList() {
        // 반환할 OrderResDto리스트 선언
        List<OrderResDto> orderResponseDtoList = new ArrayList<>();

        // 주문에 저장된 데이터 모두 가져오기
        List<Orders> ordersList = orderRepository.findAll();

        // OrderResDto에 들어갈 파라미터중 하나인 foodOrdersInfoList 선언
        List<FoodOrderResDto> foodOrderResDtoList = new ArrayList<>();
        
        // 아 사실상 registerOrders 에서 반환한 orderResDto를 List에 넣어줘서 반환해주는 거다
        // 주문에 저장된 데이터 돌린다
        for(Orders orders : ordersList){
            // Orders를 기준으로 주문 상세 정보 리스트(foodOrdersInfoList)를 가져오기
            List<FoodOrdersInfo> foodOrdersInfoList = foodOrderInfoRepository.findAllByOrders(orders);
            for(FoodOrdersInfo foodOrdersInfo : foodOrdersInfoList){
                int quantity = foodOrdersInfo.getQuantity();
                int price = foodOrdersInfo.getPrice();
                Food food = foodOrdersInfo.getFood();

                // 주문 상세정보 리스트에 데이터 가져와서 builder 적용
                FoodOrdersInfo foodOrdersInfos = FoodOrdersInfo.builder()
                        .quantity(foodOrdersInfo.getQuantity())
                        .price(foodOrdersInfo.getPrice())
                        .food(foodOrdersInfo.getFood())
                        .orders(orders) // 위에서 만든 orders 정보를 FoodOrdersInfo에 있는 orders에 넣어준다.
                        .build();

                FoodOrderResDto foodOrderResDto = new FoodOrderResDto(foodOrdersInfos);
                foodOrderResDtoList.add(foodOrderResDto);
            }

            OrderResDto orderResDto = new OrderResDto(orders, foodOrderResDtoList);
            orderResponseDtoList.add(orderResDto);  // for문 돌면서 결과를 리스트에 담아준다.
        }

        return orderResponseDtoList;
    }
}
