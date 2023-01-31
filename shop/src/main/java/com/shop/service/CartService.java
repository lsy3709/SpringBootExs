package com.shop.service;

import com.shop.dto.CartItemDto;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;

import com.shop.dto.CartDetailDto;
import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.util.StringUtils;
import com.shop.dto.CartOrderDto;
import com.shop.dto.OrderDto;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCart(CartItemDto cartItemDto, String email){

    	// 2번 -> 3번 
    	// 장바구니에 담긴 상품의 객체 가지고 오기. 
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        // 장바구니 추가한 유저.
        Member member = memberRepository.findByEmail(email);

        // 유저의 장바구니의 널 체크. 
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
        	// 장바구니에 상품을 처음 담을 때, 장바구니 없는 경우
        	// 장바구니 새로 만들기. 
            cart = Cart.createCart(member);
            // 디비에 반영. 
            cartRepository.save(cart);
        }

        // 장바구니가 있다면
        // 장바구니에 담겨진 상품을 가지고 오기. 장바구니_아이템.
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        // 장바구니에 구매한 상품이 존재한다면, 수량을 증가시키기.
        if(savedCartItem != null){
        	
            savedCartItem.addCount(cartItemDto.getCount());
            
            return savedCartItem.getId();
        } else {
        	//장바구니에 없는 상품을 추가시. 
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            //cartItem 테이블에 반영하기. 
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
        return cartDetailDtoList;
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        Member curMember = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId) {
    	// 삭제시 요청한 장바구니_상품 아이디로 검색해서, 디비에 있는지 조회.
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        // 실제 삭제 로직. 
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
        	// 장바구니 아이템이 있는지 여부 조회. 
            CartItem cartItem = cartItemRepository
                            .findById(cartOrderDto.getCartItemId())
                            .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            // 리스트에 추가하는 작업.
            orderDtoList.add(orderDto);
        }
// 내일 이어서 설명 다시 하기. 
        Long orderId = orderService.orders(orderDtoList, email);
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                            .findById(cartOrderDto.getCartItemId())
                            .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }

}