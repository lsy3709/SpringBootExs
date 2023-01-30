package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

	// 일반 데이터 : 입력 값, 숫자, 문자열
	private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    // 파일 데이터, 여러 이미지를 처리 하기 위한 리스트.
    // 리스트 안에, 형 : ItemImgDto 의 객체를 하나씩 가지는 리스트.
    // 반복문 , 뷰에서 타임리프 반복문 작업을 할 때, 리스트에서 하나씩 꺼내는 작업. 
    // ItemImgDto 라는 형에 다시 재할당 하는 부분. 
    
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    // 해당 이미지의 아이디만 모아서 가지고 있는 리스트. 
    private List<Long> itemImgIds = new ArrayList<>();

    // 뷰에서 입력된 값, 일반 데이터, 파일 데이터 부분을 , ItemFormDto 객체 자동으로 매핑을 합니다. 
    // 저장을 누러서, 일반 데이터, 파일 데이터 컨트롤러에 전달하면, 알아서 해당 객체 타입으로 자동으로 매핑.
    private static ModelMapper modelMapper = new ModelMapper();

// 상품을 등록시, ItemFormDto : this, <---> Item , 매핑
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

 //  Item<---> ItemFormDto :  , 매핑
    public static ItemFormDto of(Item item){
        return modelMapper.map(item,ItemFormDto.class);
    }

}