package com.ironplug.service.business;

import com.ironplug.entity.business.Image;
import com.ironplug.entity.business.Kontrol;
import com.ironplug.entity.business.Title;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.payload.request.KontrolRequest;
import com.ironplug.payload.response.KontrolResponse;
import com.ironplug.repository.business.KontrolRepository;

import java.util.ArrayList;
import java.util.List;

public class KontrolService {

private ImageService imageService;
private TitleService titleService;
private KontrolRepository kontrolRepository;






    public List<KontrolResponse> kresteKontrol(List<KontrolRequest> kontrols) {
        List<KontrolResponse> kresteKontrolResponse = new ArrayList<>();

        for (KontrolRequest kontrolRequest : kontrols) {
            if (!kontrolRequest.getKontrolEdildi()) {
                throw new RuntimeException(ErrorMessages.KONTROL_BOS);
            }

            // Image ve Title servislerinden gerekli verileri al
            Image image = imageService.getImageById(kontrolRequest.getImageId());
            Title title = titleService.getTitlebyID(kontrolRequest.getBaslikId());

            if (image == null || title == null) {
                throw new RuntimeException(ErrorMessages.IMAGE_VEYA_TITLE_BOS);
            }

            // Entity oluştur ve veritabanına kaydet
            Kontrol kontrol = new Kontrol();
            kontrol.setKontrolEdildi(kontrolRequest.getKontrolEdildi());
            kontrol.setImage(image);
            kontrol.setBaslik(title);
            kontrol.setContentName(kontrolRequest.getContentName());

            // Repository kullanarak entity'i kaydet
            kontrol = kontrolRepository.save(kontrol);

            // Entity'i Response'a çevir ve listeye ekle
            KontrolResponse response = new KontrolResponse();
            response.setId(kontrol.getId());  // Kaydedilen entity'nin ID'sini ekleyelim
            response.setKontrolEdildi(kontrol.getKontrolEdildi());
            response.setContentName(kontrol.getContentName());

            kresteKontrolResponse.add(response);
        }

        return kresteKontrolResponse;
    }


}
