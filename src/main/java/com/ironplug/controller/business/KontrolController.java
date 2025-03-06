package com.ironplug.controller.business;


import com.ironplug.entity.business.Kontrol;
import com.ironplug.payload.request.KontrolRequest;
import com.ironplug.payload.response.KontrolResponse;
import com.ironplug.service.business.KontrolService;
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
public class KontrolController {

private final KontrolService kontrolService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @PostMapping()
    public ResponseEntity<List<KontrolResponse>> kontrolOlustur(@RequestBody List<KontrolRequest> kontrolsrequest) {
        List<KontrolResponse> kontrols1 = kontrolService.kresteKontrol(kontrolsrequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(kontrols1);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @GetMapping("/{titleId}")
    public ResponseEntity<List<KontrolResponse>> getAllKontrols(@PathVariable Long titleId,HttpServletRequest httpServletRequest) {
        List<KontrolResponse> kontrols = kontrolService.getAllKontrols(titleId,httpServletRequest);
        return ResponseEntity.ok(kontrols);
    }

}
