package com.shop.service;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
	//application.properties 라는 파일안에 있는 값을 가져왔음. 
	//itemImgLocation=C:/shop/item
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        System.out.println("ItemImgService: oriImgName : "+ oriImgName);
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        // 파일이 있다면.
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                    itemImgFile.getBytes());
            System.out.println("ItemImgService: imgName : "+ imgName);
            //WebMvcConfig 파일에 설정 부분에 
            // uploadPath=file:///C:/shop/
            // registry.addResourceHandler("/images/**")
            // .addResourceLocations(uploadPath);
            imgUrl = "/images/item/" + imgName;
            System.out.println("ItemImgService: imgUrl : "+ imgUrl);
        }

        //상품 이미지 정보 저장
        // 엔티티 클래스 객체에 멤버로 값을 재할당.
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        // itemImg 이미지를 데이터베이스에 반영. 
        itemImgRepository.save(itemImg);
    }

    // 업데이트 로직 부분, 만약에 수정 부분인데, 기존의 파일 이미지를 삭제 후 , 새로 작성하는 부분.
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation+"/"+
                        savedItemImg.getImgName());
            }

            // 수정 시, 새로 입력된 파일의 이미지를 다시 수정하는 역할. 
            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

}