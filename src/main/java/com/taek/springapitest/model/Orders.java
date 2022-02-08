package com.taek.springapitest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Orders {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // nullable: null 허용 여부, default가 true
    // unique: 중복 허용 여부 (false 일때 중복 허용), default가 false
    //@Column(nullable = false, unique = true)
    //private Long restaurantId;

    @Column(nullable = false)
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    //private List<Restaurant> restaurants;

    public Orders(Restaurant restaurant, int totalPrice){
        this.restaurant = restaurant;
        this.totalPrice = totalPrice;
    }

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "orders", cascade = CascadeType.REMOVE)
    private List<Food> food = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "restaurant_id") // 연관 테이블의 mappedBy = "orders" 에 있는 orders와 뒤에 _id를 붙이면 되는거 아닌가?
    private Restaurant restaurant;*/


    // 연관 관계의 주인이 아닌 객체에서 mappedBy 속성을 사용해서 주인을 지정해줘야합니다. (양방향일 때)
    // TIP : 외래 키가 있는 곳을 연관 관계의 주인으로 정하면 됩니다. 무조건 !

}
