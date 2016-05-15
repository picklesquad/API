package com.pickle.Controller;

import com.pickle.Domain.GcmBody;
import com.pickle.Util.GCM.GcmBodySingle;
import com.pickle.Util.GCM.GcmRequestEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * Created by andrikurniawan.id@gmail.com on 5/14/2016.
 */
public class GcmPost {

    public static String postToGcm(Map<String,String> body){
        final String GCM_POST_URI = "https://gcm-http.googleapis.com/gcm/send";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","key=AIzaSyDWSGYqGuphLy-OI5hc9rmIOO7NYz7P2H0");
        headers.set("Content-Type","application/json");
        HttpEntity<?> entity = new HttpEntity<Object>(body,headers);
        System.out.println(entity.getHeaders());
        System.out.println(entity.getBody());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> respEntity = restTemplate.exchange(GCM_POST_URI, HttpMethod.POST, entity, String.class);
        return respEntity.getBody();
    }
}