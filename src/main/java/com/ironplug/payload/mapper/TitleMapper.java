package com.ironplug.payload.mapper;

import com.ironplug.entity.business.Title;
import com.ironplug.entity.user.User;
import com.ironplug.payload.request.busines.TitleRequest;
import com.ironplug.payload.response.business.TitleResponse;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TitleMapper {

    public Title mapTitleRequestToTitle(TitleRequest titleRequest, User user) {
        return Title.builder()
                .title_name(titleRequest.getTitle_name())
                .createAt(ZonedDateTime.now()) // Şu anki zamanı atar (Zone bilgisi dahil)
                .user(user)
                .build();
    }

    public List<TitleResponse> maptitleToTitleListResponse(List<Title> titleList) {
        return titleList.stream()
                .map(title -> TitleResponse.builder()
                        .title_name(title.getTitle_name())
                        .build())
                .collect(Collectors.toList());
    }
}
