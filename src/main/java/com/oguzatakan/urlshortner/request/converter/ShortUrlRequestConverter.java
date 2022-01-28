package com.oguzatakan.urlshortner.request.converter;

import com.oguzatakan.urlshortner.model.ShortUrl;
import com.oguzatakan.urlshortner.request.ShortUrlRequest;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlRequestConverter {

    public ShortUrl convertToEntity(ShortUrlRequest shortUrlRequest){
        return ShortUrl.builder()
                .url(shortUrlRequest.getUrl())
                .code(shortUrlRequest.getCode())
                .build();
    }
}
