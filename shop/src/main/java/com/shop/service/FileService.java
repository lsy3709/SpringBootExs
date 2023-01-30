package com.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

	//byte[] fileData : 파일 서비스에서 , 해당 이미지 파일을 바이트로 읽어서, 배열에 담아 놓았음. 
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        // 0f24f09f-a021-40f6-a28f-c82a4333f597.jpg
    	UUID uuid = UUID.randomUUID();
    	// 확장자만 출력 되는 부분 
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // extension : .jpg
        System.out.println("extension : "+ extension);
        //savedFileName = 0f24f09f-a021-40f6-a28f-c82a4333f597.jpg
        String savedFileName = uuid.toString() + extension;
        
        System.out.println("uploadPath : "+ uploadPath);
        // 경로 콘솔에 확인 해보기.
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        //
        System.out.println("fileUploadFullUrl : "+ fileUploadFullUrl);
        
        // 바이트 단위로 해당 경로 출력하기 위한 객체.
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        // byte[] fileData 형의 배열에 쓰기. 
        // 메모리에 상에 해당 경로로 출력.
        fos.write(fileData);
        // 자원 반납.
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
    	// 실제 filePath 경로에 파일이 있어야, 메모리에 올릴수 있음. 
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}