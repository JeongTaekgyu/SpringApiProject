package com.taek.springapitest.model;

import com.taek.springapitest.dto.FoodDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor // @Builder 사용하려면 이게 있어야 하는데 왜지?
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Food {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)   // 음식명 유니크해?
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Food(FoodDto foodDto){
        this.id = foodDto.getRestaurantId();
        this.name = foodDto.getName();
        this.price = foodDto.getPrice();

    }

    //@Column(nullable = false, unique = true)
    //private Long restaurantId;

    /*@ManyToOne  // Food가 restaurant 객체를 가지고 있다.
    @JoinColumn(name = "restaurant_id", nullable = false) // 포함 대상 정보는 restaurant_id 기록!
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // 포함 대상 정보는 food_id 기록!
    private Orders orders;*/

    // 연관 관계의 주인이 아닌 객체에서 mappedBy 속성을 사용해서 주인을 지정해줘야합니다. (양방향일 때)
    // TIP : 외래 키가 있는 곳을 연관 관계의 주인으로 정하면 됩니다. 무조건 !
}
