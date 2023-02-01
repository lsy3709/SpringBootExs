package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    //방향 설정이 됨. 외래키 설정이 됨. 주
    // 임의로 연관관계에서 표현 하기를 : 주
    @ManyToOne(fetch = FetchType.LAZY)
    //@ManyToOne()
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격

    private int count; //수량

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());
        // 주문 후 재고 수량을 주문 수량 만큼 빼는 작업.
        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }
    // Order 해서 호출해서 왔음. 
// orderitem.getItem. -> Item 에 있는 addStock(수량);
    public void cancel() {
        this.getItem().addStock(count);
    }

}