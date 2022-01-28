package com.oguzatakan.urlshortner.service;

import com.oguzatakan.urlshortner.exception.CodeAlreadyExists;
import com.oguzatakan.urlshortner.exception.ShortUrlNotFoundException;
import com.oguzatakan.urlshortner.model.ShortUrl;
import com.oguzatakan.urlshortner.repository.ShortUrlRepository;
import com.oguzatakan.urlshortner.util.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepository repository;
    private final RandomStringGenerator randomStringGenerator;


    public ShortUrlService(ShortUrlRepository repository, RandomStringGenerator randomStringGenerator) {
        this.repository = repository;
        this.randomStringGenerator = randomStringGenerator;
    }

    public List<ShortUrl> getAllShortUrl() {
        return repository.findAll();
    }

    public ShortUrl getUrlByCode(String code) {
        return repository.findAllByCode(code).orElseThrow(()->new ShortUrlNotFoundException("Url not found"));
    }

    public ShortUrl create(ShortUrl shortUrl) {
        if (shortUrl.getCode() == null || shortUrl.getCode().isEmpty()){
            shortUrl.setCode(generateCode());
        }
        else if (repository.findAllByCode(shortUrl.getCode()).isPresent()){
            throw new CodeAlreadyExists("Code already exists");
        }
        shortUrl.setCode(shortUrl.getCode().toUpperCase());
        return repository.save(shortUrl);
    }

    private String generateCode() {
        String code;

        do {
            code = randomStringGenerator.generateRandomString();
        }
        while (repository.findAllByCode(code).isPresent());



        return code;
    }
}
