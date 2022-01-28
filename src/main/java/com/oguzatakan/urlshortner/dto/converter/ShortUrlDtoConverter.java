package com.oguzatakan.urlshortner.dto.converter;


import com.oguzatakan.urlshortner.dto.ShortUrlDto;
import com.oguzatakan.urlshortner.model.ShortUrl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShortUrlDtoConverter {

    public ShortUrlDto convertToDto(ShortUrl shortUrl){
        return ShortUrlDto.builder()
                .id(shortUrl.getId())
                .url(shortUrl.getUrl())
                .code(shortUrl.getCode())
                .build();
    }

    public List<ShortUrlDto> convertToDto(List<ShortUrl> shortUrl){
        return shortUrl.stream()
                .map(x-> convertToDto(x))
                .collect(Collectors.toList());
    }



}
