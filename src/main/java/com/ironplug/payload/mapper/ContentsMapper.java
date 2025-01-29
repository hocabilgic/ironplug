package com.ironplug.payload.mapper;


import com.ironplug.entity.business.Contents;
import com.ironplug.entity.business.Title;
import com.ironplug.payload.request.busines.ContentsRequest;
import com.ironplug.payload.response.business.ContentsResponse;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContentsMapper {


    public Contents mapContentRequestToContens(ContentsRequest contentsRequest, Title title){

        return Contents.builder()
                .content_name(contentsRequest.getContent_name())
                .createAt(ZonedDateTime.now())
                .title(title)
                .build();
    }

    public List<ContentsResponse> mapContentsToContentListResponse(List<Contents> contentsList) {
        return contentsList.stream()
                .map(contents -> ContentsResponse.builder()
                        .content_name(contents.getContent_name())
                        .build())
                .collect(Collectors.toList());
    }


}
