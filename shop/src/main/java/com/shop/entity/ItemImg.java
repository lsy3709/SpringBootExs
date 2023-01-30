package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item_img")
@Getter @Setter
// 종합선물 세트 
// get, set, eqaul, toString , hash
// @Data
public class ItemImg extends BaseEntity{
//BaseEntity 추상 클래스 , 공통 기능을 묶었다. 
	// 시간에 관련된 멤버들. 등록일, 수정일 등. 
	// 스프링 시스템상에 등록이 되어 있고, 리스너로 동작하고 있고,
	// 해당 name 필드로 확인하고 있다. 
	// 로그인 시 , 멤버 정보는 시큐리티가 정보를 가지고있다.
	
	// 참고 
	// 자동 번호 증가 부분은 PK에서만 설정이 가능함. 
    @Id
    @Column(name="item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    private String repimgYn; //대표 이미지 여부

    // 지연로딩, 조회시 연관된 테이블 모두 조회 안하기 위해서.
    // @ManyToOne 의 기본값은 즉시로딩.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id") // 외래키 설정 부분. 
    private Item item;

    // 엔티티 클래스 부분.
    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}