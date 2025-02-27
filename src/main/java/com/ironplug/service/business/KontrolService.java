package com.ironplug.service.business;

import com.ironplug.entity.business.Image;
import com.ironplug.entity.business.Kontrol;
import com.ironplug.entity.business.Title;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.payload.request.KontrolRequest;
import com.ironplug.payload.response.KontrolResponse;
import com.ironplug.repository.business.KontrolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            Image image = imageService.getImageById(kontrolRequest.getImageId());
            if (image == null) {
                throw new RuntimeException(ErrorMessages.IMAGE_BOS);
            }

            Title title = titleService.getTitlebyID(kontrolRequest.getBaslikId());
            if (title == null) {
                throw new RuntimeException(ErrorMessages.TITLE_BOS);
            }

            // Entity oluştur ve veritabanına kaydet
            Kontrol kontrol = new Kontrol();
            kontrol.setKontrolEdildi(kontrolRequest.getKontrolEdildi());
            kontrol.setImages(List.of(image)); // Tek nesneyi listeye çevirme
            kontrol.setBaslik(title);
            kontrol.setContentName(kontrolRequest.getContentName());

            // Repository kullanarak entity'i kaydet
            kontrol = kontrolRepository.save(kontrol);

            // Entity'i Response'a çevir ve listeye ekle
            KontrolResponse response = new KontrolResponse();
            response.setId(kontrol.getId());
            response.setKontrolEdildi(kontrol.getKontrolEdildi());
            response.setContentName(kontrol.getContentName());
            response.setCreateAt(kontrol.getCreateAt());  // Eğer createAt otomatik set edilmiyorsa, LocalDateTime.now() eklenebilir
            response.setUpdateAt(kontrol.getUpdateAt());

            kresteKontrolResponse.add(response);
        }

        return kresteKontrolResponse;
    }
}
