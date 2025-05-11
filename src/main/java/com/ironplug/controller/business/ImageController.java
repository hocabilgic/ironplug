package com.ironplug.controller.business;


import com.ironplug.entity.business.Image;
import com.ironplug.service.business.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Görüntü Yönetimi", description = "Görüntü yükleme, güncelleme, silme ve alma API'leri")
public class ImageController {

    private final ImageService imageService;

    //I-01 /images/:imageId-get Bir reklamın görüntüsünü alacak
    @GetMapping("/{imageId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "Görüntü getirme", description = "Belirli bir ID'ye sahip görüntüyü getirir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Görüntü başarıyla getirildi",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Görüntü bulunamadı")
    })
    public ResponseEntity<Image> getImageById(
            @Parameter(description = "Görüntü ID", required = true) @PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        return ResponseEntity.ok(image);
    }

    //I-02 /images/:advertId-post Bir ürünün resim(ler)ini yükleyecektir
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PostMapping()
    @Operation(summary = "Görüntü yükleme", description = "Bir veya birden fazla görüntü yükler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Görüntüler başarıyla yüklendi",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "400", description = "Geçersiz dosya formatı veya yükleme hatası")
    })
    public ResponseEntity<List<Long>> uploadImages(
            @Parameter(description = "Yüklenecek görüntü dosyaları", required = true) @RequestParam("images") List<MultipartFile> images) {
        List<Long> imageIds = imageService.uploadImages(images);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageIds);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PostMapping("/{imageId}")
    @Operation(summary = "Görüntü güncelleme", description = "Mevcut bir görüntüyü günceller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Görüntüler başarıyla güncellendi",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Görüntü bulunamadı")
    })
    public ResponseEntity<List<Long>> updateImages(
            @Parameter(description = "Güncellenecek görüntü dosyaları", required = true) @RequestParam("images") List<MultipartFile> images,
            @Parameter(description = "Güncellenecek görüntü ID", required = true) @PathVariable Long imageId) {
        List<Long> imageIds = imageService.updateImages(images, imageId);
        return ResponseEntity.status(HttpStatus.OK).body(imageIds);
    }

    //I-03 /images/:image_ids-delete
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @DeleteMapping("/{imageIds}")
    @Operation(summary = "Görüntü silme", description = "Belirtilen ID'lere sahip görüntüleri siler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Görüntüler başarıyla silindi"),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Görüntü bulunamadı")
    })
    public ResponseEntity<Void> deleteImages(
            @Parameter(description = "Silinecek görüntü ID'leri", required = true) @PathVariable List<Long> imageIds) {
        imageService.deleteImages(imageIds);
        return ResponseEntity.noContent().build();
    }

    // I-04 /images/:imageId-put Bir görüntünün öne çıkan alanını ayarlayacaktır
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PutMapping("/{imageId}")
    @Operation(summary = "Öne çıkan görüntü ayarlama", description = "Belirtilen görüntüyü öne çıkan olarak işaretler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Görüntü özelliği başarıyla güncellendi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Görüntü bulunamadı")
    })
    public ResponseEntity<String> setFeaturedImage1(
            @Parameter(description = "Öne çıkan olarak işaretlenecek görüntü ID", required = true) @PathVariable Long imageId) {
        imageService.setFeaturedImage(imageId);
        return ResponseEntity.status(HttpStatus.OK).body("Image feature updated successfully.");
    }
}
