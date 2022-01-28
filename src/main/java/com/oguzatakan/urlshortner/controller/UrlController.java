package com.oguzatakan.urlshortner.controller;

import com.oguzatakan.urlshortner.dto.ShortUrlDto;
import com.oguzatakan.urlshortner.dto.converter.ShortUrlDtoConverter;
import com.oguzatakan.urlshortner.model.ShortUrl;
import com.oguzatakan.urlshortner.request.ShortUrlRequest;
import com.oguzatakan.urlshortner.request.converter.ShortUrlRequestConverter;
import com.oguzatakan.urlshortner.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping
public class UrlController {


    private final ShortUrlDtoConverter shortUrlDtoConverter;
    private final ShortUrlRequestConverter shortUrlRequestConverter;
    private final ShortUrlService service;

    public UrlController(ShortUrlDtoConverter shortUrlDtoConverter, ShortUrlRequestConverter shortUrlRequestConverter, ShortUrlService service) {
        this.shortUrlDtoConverter = shortUrlDtoConverter;
        this.shortUrlRequestConverter = shortUrlRequestConverter;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ShortUrlDto>> getAllUrls(){
        return new ResponseEntity<List<ShortUrlDto>>(
                shortUrlDtoConverter.convertToDto(service.getAllShortUrl()), HttpStatus.OK
        );
    }

    @GetMapping("show/{code}")
    public ResponseEntity<ShortUrlDto> getUrlByCode(@Valid @NotEmpty @PathVariable String code) {
        return new ResponseEntity<ShortUrlDto>(
                shortUrlDtoConverter.convertToDto(service.getUrlByCode(code)), HttpStatus.OK
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<ShortUrlDto> redirect(@Valid @NotEmpty @PathVariable String code) throws URISyntaxException {

        ShortUrl shortUrl = service.getUrlByCode(code);

        URI uri = new URI(shortUrl.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders,HttpStatus.SEE_OTHER);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ShortUrlRequest shortUrlRequest){

        ShortUrl shortUrl = shortUrlRequestConverter.convertToEntity(shortUrlRequest);

        return new ResponseEntity<ShortUrlDto>(shortUrlDtoConverter.convertToDto(service.create(shortUrl)),HttpStatus.CREATED);

    }

}
