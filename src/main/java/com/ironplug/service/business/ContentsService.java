package com.ironplug.service.business;


import com.ironplug.entity.business.Contents;
import com.ironplug.entity.business.Title;
import com.ironplug.entity.user.User;
import com.ironplug.payload.helpers.MethodHelper;
import com.ironplug.payload.mapper.ContentsMapper;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.payload.messeges.SuccessMessages;
import com.ironplug.payload.request.busines.ContentsRequest;
import com.ironplug.payload.response.business.ContentsResponse;
import com.ironplug.repository.business.ContentsRepository;
import com.ironplug.repository.business.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;


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
        if (email == null || email.isBlank()) {
            throw new RuntimeException(ErrorMessages.EMAIL_NOT_FOUND);
        }

        User user = methodHelper.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException(ErrorMessages.USER_NOT_FOUND);
        }

        Title title = titleRepository.findById(titleId)
                .orElseThrow(() -> new RuntimeException(String.format(ErrorMessages.TITLE_NOT_FOUND, titleId)));

        Contents contents=contentsMapper.mapContentRequestToContens(contentsRequest,title);

        if (!(title.getUser().getId().equals(user.getId()))) {
            throw new RuntimeException(ErrorMessages.ONLY_TITLE_OWNER_CAN_PERFORM_THIS_ACTION);
        }

        contentsRepository.save(contents);
        return CompletableFuture.completedFuture(SuccessMessages.CONTENT_SAVED_SUCCESSFULLY);
    }

    public CompletableFuture<String> updateContents(ContentsRequest contentsRequest,
                                                    HttpServletRequest httpServletRequest,
                                                    Long id) {

        String email = (String) httpServletRequest.getAttribute("email");
        if (email == null) {
            throw new RuntimeException(ErrorMessages.USER_NOT_FOUND);
        }

        User user = methodHelper.findUserByEmail(email);
        if (user == null ) {
            throw new RuntimeException(ErrorMessages.USER_NOT_FOUND);
        }
        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format(ErrorMessages.CONTENTS_NOT_FOUND, id)));

        // Başlık sahibi kontrolü
        if (!(contents.getTitle().getUser().getId().equals(user.getId()))) {
            throw new RuntimeException(ErrorMessages.ONLY_TITLE_OWNER_CAN_PERFORM_THIS_ACTION);
        }

        contents.setContent_name(contentsRequest.getContent_name());
        contents.setUpdateAt(ZonedDateTime.now());

        contentsRepository.save(contents);
        return CompletableFuture.completedFuture(SuccessMessages.CONTENTS_UPDATED_SUCCESSFULLY);
    }


    @Async
    public CompletableFuture<List<ContentsResponse>> getContentList(HttpServletRequest httpServletRequest,
                                                                    Long titleId) {
        return CompletableFuture.supplyAsync(() -> {
            String email = (String) httpServletRequest.getAttribute("email");
            if (email == null) {
                throw new RuntimeException(ErrorMessages.USER_NOT_FOUND);
            }

            User user = methodHelper.findUserByEmail(email);
            if (user == null ) {
                throw new RuntimeException(ErrorMessages.USER_NOT_FOUND);
            }

            Title title = titleRepository.findById(titleId)
                    .orElseThrow(() -> new RuntimeException(String.format(ErrorMessages.TITLE_NOT_FOUND, titleId)));


            List<Contents> contentsList=contentsRepository.findByTitleId(titleId);

            // Başlık sahibi kontrolü
            if (!title.getUser().getId().equals(user.getId())) {
                throw new RuntimeException(ErrorMessages.ONLY_TITLE_OWNER_CAN_PERFORM_THIS_ACTION);
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


    public CompletableFuture<String> deleteContents(  Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {

                if (!contentsRepository.existsById(id)) {
                    throw new RuntimeException(String.format(ErrorMessages.CONTENTS_NOT_FOUND, id));
                }

                contentsRepository.deleteById(id);

                return ErrorMessages.DELETE_SUCCESSFUL;

            } catch (Exception e) {
                // Hata durumunda mesaj döndür
                return ErrorMessages.ERROR_OCCURRED + e.getMessage();
            }
        });


    }
}





