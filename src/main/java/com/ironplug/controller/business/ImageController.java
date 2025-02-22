package com.ironplug.controller.business;


import com.ironplug.entity.business.Image;
import com.ironplug.service.business.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    //I-01 /images/:imageId-get Bir reklamın görüntüsünü alacak
    @GetMapping("/{imageId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<Image> getImageById(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        return ResponseEntity.ok(image);
    }

    //I-02 /images/:advertId-post Bir ürünün resim(ler)ini yükleyecektir
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PostMapping()
    public ResponseEntity<List<Long>> uploadImages(@RequestParam("images") List<MultipartFile> images
                                                              ) {

        List<Long> imageIds = imageService.uploadImages(images);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageIds);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PostMapping("/{imageId}")
    public ResponseEntity<List<Long>> updateImages(@RequestParam("images") List<MultipartFile> images,@PathVariable Long imageId
    ) {

        List<Long> imageIds = imageService.updateImages(images,imageId);
        return ResponseEntity.status(HttpStatus.OK).body(imageIds);
    }


    //I-03 /images/:image_ids-delete
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @DeleteMapping("/{imageIds}")
    public ResponseEntity<Void> deleteImages(@PathVariable List<Long> imageIds) {
        imageService.deleteImages(imageIds);
        return ResponseEntity.noContent().build();
    }


        // I-04 /images/:imageId-put
   //    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN','CUSTOMER')")
   //    @PutMapping("/{imageId}")
   //    public ImageResponse setFeaturedImage(@PathVariable Long imageId) {
   //    return  null;// imageService.setFeaturedImage(imageId);
   //}

        // I-04 /images/:imageId-put Bir görüntünün öne çıkan alanını ayarlayacaktır
        @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
        @PutMapping("/{imageId}")
        public ResponseEntity<String> setFeaturedImage1(@PathVariable Long imageId) {
            imageService.setFeaturedImage(imageId);
            return ResponseEntity.status(HttpStatus.OK).body("Image feature updated successfully.");
        }
}
