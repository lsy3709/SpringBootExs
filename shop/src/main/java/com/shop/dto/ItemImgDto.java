package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {
	//bread1.jpg 예

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    // 들어오는 값의 타입은 엔티티 클래스 타입 : ItemImg
    // of 메서드 호출 후, 타입이 변환 됨 : ItemImgDto
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg,ItemImgDto.class);
    }

}