package com.taek.springapitest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Food {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private Long restaurantId;

    @Column(nullable = false)   // 음식명 유니크해?
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;


    /*
    @ManyToOne
    @JoinColumn(name = "restaurantId", nullable = false) // 포함 대상 정보는 restaurant_id 기록!
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // 포함 대상 정보는 food_id 기록!
    private Orders orders;
    */
    // 연관 관계의 주인이 아닌 객체에서 mappedBy 속성을 사용해서 주인을 지정해줘야합니다. (양방향일 때)
    // TIP : 외래 키가 있는 곳을 연관 관계의 주인으로 정하면 됩니다. 무조건 !
}
