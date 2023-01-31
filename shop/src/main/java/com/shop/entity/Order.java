package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

//    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST
//            , orphanRemoval = true, fetch = FetchType.LAZY)
    //방향 설정이 됨. 외래키 설정은 없다(mappedBy). 보조 
    // 임의로 연관관계에서 표현 하기를 : 보조
    // 읽기는 가능함.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        //현재 클래스 Order 엔티티 클래스 , this : order
        // OrderItem <----> Order 양방향 설정이 되어 있다. 
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);

        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
    // OrderService에서 호출해서 왔음. 
// 주문의 상태를 ORDER -> CANCEL 로 상태 변경
    // 상품의 재고 수량의 원래대로 복구.
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        
        //주문안에 있는 주문상품 각각에 cancel 메소드를 호출함. 
        for (OrderItem orderItem : orderItems) {
        	// 현재 위치 Order 엔티티클래스에서 
        	// orderItem 
            orderItem.cancel();
        }
    }

}