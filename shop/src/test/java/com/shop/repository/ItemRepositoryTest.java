package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.shop.entity.QItem;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

// 단위 테스트를 하기위한 애너테이션 명령어
@SpringBootTest
// 단위 테스를 적용하기 위한 관련 설정 파일 위치 알려주기. 
// 기존 H2 -> Mysql 변경.
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

	// DI 주입. 포함관계, 가져다 쓰기. 
    @Autowired
    ItemRepository itemRepository;

    // 영속성 컨텍스트 작업을 알리는 용도. 
    @PersistenceContext
    EntityManager em;

    //  @Test 표기 하면 해당 메서드를 단위 테스트를 실행 하겠다. 
    @Test
    // @DisplayName("상품 저장 테스트") 라벨지. 해당 단위테스트의 이름 정도. 
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품11");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품11 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    // 메서드 에   @Test 없음. 
    // 이유가. findByItemNmTest 메서드안에 들어가 있었음. 
    // 조회를 할 때 마다, 계속 해서 해당 디비가 있는 10개 나 있음. 
    @Test
    public void createItemList(){
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100); item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
    	// H2 db 의 설정이 데이터베이스 메모리 상에서 저장
    	// 이제는 직접 디비에 저장을 하기 때문에, 조회만 해도 됨. 
    	
    	// 리로드 했을 때 ddl-auto : create 설정 하면, 디비가 리셋
        // 해당 메서드에서 상품 10개를 디비에 저장하는 메서드. 
    	//this.createItemList();
    	//this.createItemTest();
        
    	// 상품이름으로 디비에서 검색 후, 결과를 받는 메서드. 
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
//        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
//        this.createItemList();
    	//쿼리 메소드 속성 값 테스트 중
    	// 디비에서 검색 조건으로 검색 결과를 가져와서. 
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        // 반복문으로 콘솔에 출력 중. 
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    // 쿼리 메소드 속성을 샘플 유형을 잘봐야 함. 
    // findByPriceLessThanOrderByPriceDesc <---- 이 모양을 잘보아야 합니다. 
    public void findByPriceLessThanOrderByPriceDesc(){
//        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest(){
        //this.createItemList();findByItemDetailByNative
    	//ex1 JPQL
        //List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
    	//ex2 nativeQuery = true
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        //this.createItemList();
    	// em -> 영속성 컨텍스트(중간 저장소 표현을 함) , 객체를 가지고 옴. 
    	// EntityManager em;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        // pom.xml 에서 com.querydsl.apt.jpa.JPAAnnotationProcessor
        // 자동으로 생성된 Q 도메인 , 객체를 전역, 상수로 선언.
        QItem qItem = QItem.item;
        JPAQuery<Item> query  = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.asc());

        List<Item> itemList = query.fetch();

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    public void createItemList2(){
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2(){

        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price));
        System.out.println(ItemSellStatus.SELL);
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        // 스프링 부트에서 미리 만들어 놓은 페이징을 쉽게 처리해주는 인터페이스 
        Pageable pageable = PageRequest.of(0, 5);
        
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + itemPagingResult. getTotalElements ());

        List<Item> resultItemList = itemPagingResult.getContent();
        for(Item resultItem: resultItemList){
            System.out.println(resultItem.toString());
        }
    }

}