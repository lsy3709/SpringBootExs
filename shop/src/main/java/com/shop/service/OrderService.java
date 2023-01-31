package com.shop.service;

import com.shop.dto.OrderDto;
import com.shop.entity.*;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.shop.dto.OrderHistDto;
import com.shop.dto.OrderItemDto;
import com.shop.repository.ItemImgRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email){

        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

    	//구매자가 구매한 물품의 주문들, 총 갯수
    	// 현재 위치 2번 서비스  -> 3번 dao = Repository
    	// 해당 구매자가 구입한 물품을 조회해서, 상품들을 가지고 옴.(해당 유저를 파악하기 위해서 이메일필요.)
    	// orders : 해당 구매자가 구입한 물품들.
    	// totalCount : 총 갯수
        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        // 리스트 orderHistDtos : 주문 기록 DTO 
        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        //
        for (Order order : orders) {
        	// orders 에서 꺼내 상품의 타입은 Entity 형이고
        	// 다시, OrderHistDto 타입으로 변환. 
            OrderHistDto orderHistDto = new OrderHistDto(order);
            
            //엔티티클래스 order , 멤버 중에서 OrderItems 라는 멤버가 있고. 
            // 해당 멤버에서 롬복 게터에 의해서, 한번에 리스트를 가지고 옴. 
            // 예) 8번 주문 중에서 하나의 주문을 선택하고, 해당 주문에 담겨있는 상품의 목록을 가지고 옴. 
            List<OrderItem> orderItems = order.getOrderItems();
            
            // 8개 주문에서 -> 하나 주문 꺼내고, 하나의 주문에서 -> 여러 개 상품들을 꺼내고.
            // 여래 개 상품에서 -> 하나씩 상품 꺼내기. 
            for (OrderItem orderItem : orderItems) {
            	
            	// 꺼낸 하나의 상품에서 해당 이미지를 가지고 옴. 
            	// 결괏값 : 상품아이디 and 대표이미지 여부 Y 인 이미지를 조회해서 가지고 옴. 
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
                        (orderItem.getItem().getId(), "Y");
                
                // 상품 정보와 상품 이미지 정보를 -> orderHistDto 담아서 
                
                // 컨트롤러에 반환해서, -> 뷰에 이 리스트 넘겨서 -> 뷰에서 사용 가능. 
                OrderItemDto orderItemDto =
                        new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            // List<OrderHistDto> orderHistDtos = new ArrayList<>(); 
            // 리스트에 추가하는 작업. 
            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email){

        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

}