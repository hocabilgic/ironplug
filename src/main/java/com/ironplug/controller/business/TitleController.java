package com.ironplug.controller.business;

import com.ironplug.payload.request.busines.TitleRequest;
import com.ironplug.payload.response.business.TitleResponse;
import com.ironplug.service.business.TitleService;
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

public class TitleController {


    private final TitleService titleService;



    // User Title kayıt eder
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public CompletableFuture<String> saveTitle(@RequestBody @Valid TitleRequest titleRequest,
                                               HttpServletRequest httpServletRequest ){

        return titleService.saveTitle(titleRequest,httpServletRequest);

    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public CompletableFuture<String> updateTitle(@RequestBody @Valid TitleRequest titleRequest,
                                                 HttpServletRequest httpServletRequest,
                                                 @PathVariable Long id){
        return titleService.updateTitle(titleRequest,httpServletRequest,id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public CompletableFuture<List<TitleResponse>> getTitleList(HttpServletRequest httpServletRequest){

        return titleService.getTitleList(httpServletRequest);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public CompletableFuture<String> deleteTitle(@PathVariable Long id){
        return titleService.deleteTitle(id);
    }


}
