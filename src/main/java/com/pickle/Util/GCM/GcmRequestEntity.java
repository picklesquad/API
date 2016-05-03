package com.pickle.Util.GCM;

import org.springframework.http.RequestEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by andrikurniawan.id@gmail.com on 5/4/2016.
 */
public class GcmRequestEntity<T> extends RequestEntity<GcmBody<T>>{

    private static final String GOOGLE_API_SERVER_KEY = " AIzaSyDpJbGVhVRaoDxH5Vm979xqLTS36e-cCUE ";
    private static final String AUTH_KEY = "Authorization";
    private static final String POST_URI = "https://gcm-http.googleapis.com/gcm/send";

    private GcmRequestEntity(GcmBody<T> body, MultiValueMap<String, String> headers, HttpMethod method, URI url){
        super(body, headers, method, url);
    }

    public static class Builder <T>{
        private HttpHeaders headers;
        private HttpMethod method;
        private GcmBody<T> gcmBody;
        private URI uri;

        public Builder(HttpMethod method, GcmBody<T> gcmBody) {
            headers = new HttpHeaders();
            headers.set(AUTH_KEY, "key=" + GOOGLE_API_SERVER_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            this.method = method;
            this.gcmBody = gcmBody;

            String uriString = "";
            switch (method) {
                case POST:
                    uriString = POST_URI;
                    break;
            }

            try {
                this.uri = new URI(uriString);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }


    }
}

