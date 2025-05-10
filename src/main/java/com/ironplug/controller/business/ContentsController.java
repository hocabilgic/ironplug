package com.ironplug.controller.business;


import com.ironplug.entity.business.Contents;
import com.ironplug.payload.request.busines.ContentsRequest;
import com.ironplug.payload.request.busines.TitleRequest;
import com.ironplug.payload.response.business.ContentsResponse;
import com.ironplug.payload.response.business.TitleResponse;
import com.ironplug.service.business.ContentsService;
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
@RequestMapping("/contents")
@RequiredArgsConstructor
@Tag(name = "Content Management", description = "İçerik yönetimi API'leri")
public class ContentsController {

    private final ContentsService contentsService;

    // User Contents kayıt eder
    @PostMapping("/{titleId}/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "Yeni içerik kaydetme", description = "Belirli bir başlığa bağlı yeni bir içerik kaydeder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "İçerik başarıyla kaydedildi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Başlık bulunamadı")
    })
    public CompletableFuture<String> saveContents(
            @Parameter(description = "İçerik bilgileri", required = true) @RequestBody @Valid ContentsRequest contentsRequest,
            HttpServletRequest httpServletRequest,
            @Parameter(description = "Başlık ID", required = true) @PathVariable Long titleId) {

        return contentsService.saveContents(contentsRequest, httpServletRequest, titleId);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "İçerik güncelleme", description = "Mevcut bir içeriği günceller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "İçerik başarıyla güncellendi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "İçerik bulunamadı")
    })
    public CompletableFuture<String> updateContents(
            @Parameter(description = "Güncellenecek içerik bilgileri", required = true) @RequestBody @Valid ContentsRequest contentsRequest,
            HttpServletRequest httpServletRequest,
            @Parameter(description = "İçerik ID", required = true) @PathVariable Long id) {
        return contentsService.updateContents(contentsRequest, httpServletRequest, id);
    }

    @GetMapping("/{titleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "İçerik listesi getirme", description = "Belirli bir başlığa ait tüm içerikleri listeler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "İçerik listesi başarıyla getirildi",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ContentsResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "Başlık bulunamadı")
    })
    public CompletableFuture<List<ContentsResponse>> getContentList(
            HttpServletRequest httpServletRequest,
            @Parameter(description = "Başlık ID", required = true) @PathVariable Long titleId) {
        return contentsService.getContentList(httpServletRequest, titleId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "İçerik silme", description = "Belirtilen ID'ye sahip içeriği siler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "İçerik başarıyla silindi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "İçerik bulunamadı")
    })
    public CompletableFuture<String> deleteContent(
            @Parameter(description = "Silinecek içerik ID", required = true) @PathVariable Long id) {
        return contentsService.deleteContents(id);
    }
}
