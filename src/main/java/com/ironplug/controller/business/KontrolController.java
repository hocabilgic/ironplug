package com.ironplug.controller.business;


import com.ironplug.entity.business.Kontrol;
import com.ironplug.payload.request.KontrolRequest;
import com.ironplug.payload.response.KontrolResponse;
import com.ironplug.service.business.KontrolService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/kontrol")
@RequiredArgsConstructor
@Tag(name = "Kontrol Yönetimi", description = "Kontrol kayıtları oluşturma ve listeleme API'leri")
public class KontrolController {

    private final KontrolService kontrolService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PostMapping()
    @Operation(summary = "Kontrol kaydı oluşturma", description = "Yeni kontrol kayıtları oluşturur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kontrol kayıtları başarıyla oluşturuldu",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = KontrolResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek")
    })
    public ResponseEntity<List<KontrolResponse>> kontrolOlustur(
            @Parameter(description = "Kontrol istekleri listesi", required = true) @RequestBody List<KontrolRequest> kontrolsrequest) {
        List<KontrolResponse> kontrols1 = kontrolService.kresteKontrol(kontrolsrequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(kontrols1);
    }
    
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("/{titleId}")
    @Operation(summary = "Kontrol kayıtlarını listeleme", description = "Belirli bir başlığa ait tüm kontrol kayıtlarını listeler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kontrol kayıtları başarıyla listelendi",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = KontrolResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Belirtilen başlık bulunamadı")
    })
    public ResponseEntity<List<KontrolResponse>> getAllKontrols(
            @Parameter(description = "Başlık ID", required = true) @PathVariable Long titleId,
            HttpServletRequest httpServletRequest) {
        List<KontrolResponse> kontrols = kontrolService.getAllKontrols(titleId, httpServletRequest);
        return ResponseEntity.ok(kontrols);
    }
}
