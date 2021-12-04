package hello.upload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// 상품 저장용 폼 객체
@Data
public class ItemForm {

    private Long id;
    private String itemName;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}
