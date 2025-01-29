package com.ironplug.service.business;


import com.ironplug.entity.business.Contents;
import com.ironplug.entity.business.Title;
import com.ironplug.entity.user.User;
import com.ironplug.payload.helpers.MethodHelper;
import com.ironplug.payload.mapper.ContentsMapper;
import com.ironplug.payload.request.busines.ContentsRequest;
import com.ironplug.payload.response.business.ContentsResponse;
import com.ironplug.payload.response.business.TitleResponse;
import com.ironplug.repository.business.ContentsRepository;
import com.ironplug.repository.business.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;
    private final MethodHelper methodHelper;
    private final ContentsMapper contentsMapper;
    private final TitleRepository titleRepository;


    @Async
    public CompletableFuture<String> saveContents(ContentsRequest contentsRequest,
                                                  HttpServletRequest httpServletRequest,
                                                  Long titleId) {

        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.findUserByEmail(email);
        Title title = titleRepository.findById(titleId)
                .orElseThrow(() -> new RuntimeException("Title with ID " + titleId + " not found."));

        Contents contents=contentsMapper.mapContentRequestToContens(contentsRequest,title);

        if (!(title.getUser().getId().equals(user.getId()))) {
            throw new RuntimeException("Bu işlem yalnızca başlık sahibi tarafından yapılabilir.");
        }

        contentsRepository.save(contents);
        return CompletableFuture.completedFuture("Title başarıyla kaydedildi.");
    }

    public CompletableFuture<String> updateContents(ContentsRequest contentsRequest,
                                                    HttpServletRequest httpServletRequest,
                                                    Long id) {

        String email = (String) httpServletRequest.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("Email bilgisi bulunamadı.");
        }

        User user = methodHelper.findUserByEmail(email);

        Contents contents=contentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contents with ID " + id + " not found."));


        // Başlık sahibi kontrolü
        if (!(contents.getTitle().getUser().getId().equals(user.getId()))) {
            throw new RuntimeException("Bu işlem yalnızca başlık sahibi tarafından yapılabilir.");
        }

        contents.setContent_name(contentsRequest.getContent_name());
        contents.setUpdateAt(ZonedDateTime.now());

        contentsRepository.save(contents);
        return CompletableFuture.completedFuture("Contents başarıyla güncellendi.");
    }


    @Async
    public CompletableFuture<List<ContentsResponse>> getContentList(HttpServletRequest httpServletRequest,
                                                                    Long titleId) {
        return CompletableFuture.supplyAsync(() -> {
            String email = (String) httpServletRequest.getAttribute("email");
            if (email == null) {
                throw new RuntimeException("Email bilgisi bulunamadı.");
            }

            User user = methodHelper.findUserByEmail(email);
            Title title = titleRepository.findById(titleId)
                    .orElseThrow(() -> new RuntimeException("Title with ID " + titleId + " not found."));


            List<Contents> contentsList=contentsRepository.findByTitleId(titleId);

            // Başlık sahibi kontrolü
            if (!title.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Bu işlem yalnızca başlık sahibi tarafından yapılabilir.");
            }

            // İçerikleri getir
            List<ContentsResponse> contentsResponseList=contentsMapper.mapContentsToContentListResponse(contentsList);

            return contentsResponseList;
        }).exceptionally(ex -> {
            // Hata durumunda boş liste döndür ve logla
            ex.printStackTrace();
            return Collections.emptyList();
        });
    }


    }





