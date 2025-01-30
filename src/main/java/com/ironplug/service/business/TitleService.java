package com.ironplug.service.business;

import com.ironplug.entity.business.Title;
import com.ironplug.entity.user.User;
import com.ironplug.payload.helpers.MethodHelper;
import com.ironplug.payload.mapper.TitleMapper;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.payload.messeges.SuccessMessages;
import com.ironplug.payload.request.busines.TitleRequest;
import com.ironplug.payload.response.business.TitleResponse;
import com.ironplug.repository.User.UserRepository;
import com.ironplug.repository.business.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    private final TitleMapper titleMapper;
    private final MethodHelper methodHelper;


    @Async
    public CompletableFuture<String> saveTitle(TitleRequest titleRequest, HttpServletRequest httpServletRequest) {
        try {

            String email = (String) httpServletRequest.getAttribute("email");

            if (email == null) {
                throw new RuntimeException(ErrorMessages.EMAIL_NOT_FOUND);
            }

            User user = methodHelper.findUserByEmail(email);
            Title title = titleMapper.mapTitleRequestToTitle(titleRequest, user);

            titleRepository.save(title);

            return CompletableFuture.completedFuture(SuccessMessages.TITLE_SAVED_SUCCESSFULLY);
        } catch (Exception e) {
            return CompletableFuture.completedFuture("Bir hata oluştu: " + e.getMessage());
        }
    }



    @Async
    public CompletableFuture<String> updateTitle(TitleRequest titleRequest, HttpServletRequest httpServletRequest, Long id) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                // Email bilgisi kontrolü
                String email = (String) httpServletRequest.getAttribute("email");
                if (email == null) {
                    throw new RuntimeException(ErrorMessages.EMAIL_NOT_FOUND);
                }

                // User'ı bulma
                User user = methodHelper.findUserByEmail(email);
                Title title = titleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(String.format(ErrorMessages.TITLE_NOT_FOUND, id)));


                // Başlık sahibi kontrolü
                if (!(title.getUser().getId().equals(user.getId()))) {
                    throw new RuntimeException(ErrorMessages.ONLY_TITLE_OWNER_CAN_PERFORM_THIS_ACTION);
                }

                // Başlık adını güncelleme ve tarih ayarlama
                title.setTitle_name(titleRequest.getTitle_name());
                title.setUpdateAt(ZonedDateTime.now());

                // Veritabanında güncelleme işlemi
                titleRepository.save(title);
                return "Title başarıyla güncellendi.";
            });

        } catch (Exception e) {
            return CompletableFuture.completedFuture("Bir hata oluştu: " + e.getMessage());
        }
    }

    @Async
    public CompletableFuture<List<TitleResponse>> getTitleList(HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.findUserByEmail(email);

        // Kullanıcıya ait başlıkları al
        List<Title> titleList = titleRepository.findAllUserTitle(user.getId());

        // Başlıkları TitleResponse listesine dönüştür
        List<TitleResponse> titleResponseList = titleMapper.maptitleToTitleListResponse(titleList);

        return CompletableFuture.completedFuture(titleResponseList);
    }


    public CompletableFuture<String> deleteTitle(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {

                if (!titleRepository.existsById(id)) {
                    throw new RuntimeException("Başlık bulunamadı.");
                }

                titleRepository.deleteById(id);

                return "Silme başarılı";

            } catch (Exception e) {
                // Hata durumunda mesaj döndür
                return "Bir hata oluştu: " + e.getMessage();
            }
        });
    }



}
