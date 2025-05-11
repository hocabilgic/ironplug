package com.ironplug.service.business;

import com.ironplug.entity.business.Image;
import com.ironplug.entity.business.Kontrol;
import com.ironplug.entity.business.Title;
import com.ironplug.entity.user.User;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.payload.request.KontrolRequest;
import com.ironplug.payload.response.KontrolResponse;
import com.ironplug.repository.business.KontrolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KontrolService {

    private final ImageService imageService;
    private final TitleService titleService;
    private final KontrolRepository kontrolRepository;

    public List<KontrolResponse> kresteKontrol(List<KontrolRequest> kontrols) {
        List<KontrolResponse> kresteKontrolResponse = new ArrayList<>();

        for (KontrolRequest kontrolRequest : kontrols) {
            if (!Boolean.TRUE.equals(kontrolRequest.getKontrolEdildi())) {
                throw new RuntimeException(ErrorMessages.KONTROL_BOS);
            }

            // Image ve Title servislerinden gerekli verileri al
            Image image = null;
            if (kontrolRequest.getImageId() != null) {
                image = imageService.getImageByIdIfNotNull(kontrolRequest.getImageId());
            }

            Title title = titleService.getTitlebyID(kontrolRequest.getBaslikId());
            if (title == null) {
                throw new RuntimeException(ErrorMessages.TITLE_BOS);
            }

            // Her istek için yeni bir Kontrol nesnesi oluştur
            Kontrol kontrol = new Kontrol();

            kontrol.setKontrolEdildi(kontrolRequest.getKontrolEdildi());
            kontrol.setImages(image);
            kontrol.setBaslik(title);
            kontrol.setContentName(kontrolRequest.getContentName());

            // Repository kullanarak entity'i kaydet
            kontrol = kontrolRepository.save(kontrol);

            // Entity'i Response'a çevir ve listeye ekle
            KontrolResponse response = new KontrolResponse();
            response.setId(kontrol.getId());
            response.setKontrolEdildi(kontrol.getKontrolEdildi());
            response.setContentName(kontrol.getContentName());
            response.setCreateAt(kontrol.getCreateAt());
            response.setUpdateAt(kontrol.getUpdateAt());
            response.setBaslikAdi(kontrol.getBaslik().getTitle_name());

            kresteKontrolResponse.add(response);
        }

        return kresteKontrolResponse;
    }

    // Her gün gece 2'de çalışacak (CRON: "0 0 2 * * *")
    @Scheduled(cron = "0 0 2 * * *")
    public void temizleKontroller() {
        ZonedDateTime birHaftaOnce = ZonedDateTime.now().minusDays(7);

        // 7 günden eski kontrolleri sil
        kontrolRepository.deleteByCreateAtBefore(birHaftaOnce);
    }

    @Transactional(readOnly = true)
    public List<KontrolResponse> getAllKontrols(Long titleId, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");

        if (email == null) {
            throw new RuntimeException(ErrorMessages.EMAIL_NOT_FOUND);
        }

        // Verilen titleId'ye sahip tüm kontrolleri al
        List<Kontrol> kontroller = kontrolRepository.findAllByBaslikId(titleId);

        if (kontroller.isEmpty()) {
            throw new RuntimeException(String.format(ErrorMessages.TITLE_NOT_FOUND, titleId));
        }

        // Kontrol entity'lerini KontrolResponse nesnelerine dönüştür
        return kontroller.stream().map(kontrol -> {
            KontrolResponse response = new KontrolResponse();
            response.setId(kontrol.getId());
            response.setKontrolEdildi(kontrol.getKontrolEdildi());
            response.setContentName(kontrol.getContentName());
            response.setCreateAt(kontrol.getCreateAt());
            response.setUpdateAt(kontrol.getUpdateAt());
            response.setBaslikAdi(kontrol.getBaslik().getTitle_name());
            return response;
        }).collect(Collectors.toList());
    }
}

