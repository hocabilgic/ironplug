package com.ironplug.controller.business;


import com.ironplug.entity.business.Contents;
import com.ironplug.payload.request.busines.ContentsRequest;
import com.ironplug.payload.request.busines.TitleRequest;
import com.ironplug.payload.response.business.ContentsResponse;
import com.ironplug.payload.response.business.TitleResponse;
import com.ironplug.service.business.ContentsService;
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
public class ContentsController {

    private final ContentsService contentsService;

    // User Title kayıt eder
    @PostMapping("/{titleId}/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public CompletableFuture<String> saveContents(@RequestBody @Valid ContentsRequest contentsRequest,
                                               HttpServletRequest httpServletRequest ,
                                               @PathVariable Long titleId){

        return contentsService.saveContents(contentsRequest,httpServletRequest,titleId);

    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public CompletableFuture<String> updateContents(@RequestBody @Valid ContentsRequest contentsRequest,
                                                    HttpServletRequest httpServletRequest,
                                                    @PathVariable Long id){
        return contentsService.updateContents(contentsRequest,httpServletRequest,id);
    }

    @GetMapping("/{titleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public CompletableFuture<List<ContentsResponse>> getContentList(HttpServletRequest httpServletRequest,
                                                                    @PathVariable Long titleId){
        return contentsService.getContentList(httpServletRequest,titleId);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public CompletableFuture<String> deleteContent(@PathVariable Long id){
        return contentsService.deleteContents(id);
    }



}
