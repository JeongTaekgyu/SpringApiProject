package com.taek.springapitest.model;

import com.taek.springapitest.dto.RestaurantDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Restaurant {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int minOrderPrice;

    @Column(nullable = false)
    private int deliveryFee;

    public Restaurant(RestaurantDto restaurantDto) {
        this.id = restaurantDto.getId();
        this.name = restaurantDto.getName();
        this.minOrderPrice = restaurantDto.getMinOrderPrice();
        this.deliveryFee = restaurantDto.getDeliveryFee();
    }


    // mappedBy를 통해 restaurant을 주인으로 지정
    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<Food> food = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private Orders orders;*/


    // 연관 관계의 주인이 아닌 객체에서 mappedBy 속성을 사용해서 주인을 지정해줘야합니다. (양방향일 때)
    // TIP : 외래 키가 있는 곳을 연관 관계의 주인으로 정하면 됩니다. 무조건 !
}
