package com.ironplug.service.business;

import com.ironplug.entity.business.Kontrol;
import com.ironplug.payload.request.KontrolRequest;
import com.ironplug.payload.response.KontrolResponse;

import java.util.List;

public class KontrolService {



    public List<KontrolResponse> kresteKontrol(List<KontrolRequest> kontrols) {


        for (KontrolRequest kontrol : kontrols) {

            kontrol.getTitleId();
            //TODO : devam eterceksin.


        }



        return null;

    }
}
