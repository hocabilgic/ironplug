package com.ironplug.controller.business;

import com.ironplug.payload.request.busines.TitleRequest;
import com.ironplug.payload.response.business.TitleResponse;
import com.ironplug.service.business.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/title")
@RequiredArgsConstructor
@Tag(name = "Başlık Yönetimi", description = "Başlık oluşturma, güncelleme ve silme API'leri")
public class TitleController {

    private final TitleService titleService;

    // User Title kayıt eder
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "Yeni başlık ekleme", description = "Kullanıcı için yeni bir başlık kaydeder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başlık başarıyla eklendi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok")
    })
    public CompletableFuture<String> saveTitle(
            @Parameter(description = "Başlık bilgileri", required = true) @RequestBody @Valid TitleRequest titleRequest,
            HttpServletRequest httpServletRequest) {
        return titleService.saveTitle(titleRequest, httpServletRequest);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "Başlık güncelleme", description = "Mevcut bir başlığı günceller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başlık başarıyla güncellendi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Başlık bulunamadı")
    })
    public CompletableFuture<String> updateTitle(
            @Parameter(description = "Güncellenecek başlık bilgileri", required = true) @RequestBody @Valid TitleRequest titleRequest,
            HttpServletRequest httpServletRequest,
            @Parameter(description = "Başlık ID", required = true) @PathVariable Long id) {
        return titleService.updateTitle(titleRequest, httpServletRequest, id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "Başlık listesi getirme", description = "Kullanıcıya ait tüm başlıkları listeler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başlık listesi başarıyla getirildi",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TitleResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok")
    })
    public CompletableFuture<List<TitleResponse>> getTitleList(
            HttpServletRequest httpServletRequest) {
        return titleService.getTitleList(httpServletRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "Başlık silme", description = "Belirtilen ID'ye sahip başlığı siler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başlık başarıyla silindi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Başlık bulunamadı")
    })
    public CompletableFuture<String> deleteTitle(
            @Parameter(description = "Silinecek başlık ID", required = true) @PathVariable Long id) {
        return titleService.deleteTitle(id);
    }
}
