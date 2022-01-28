package com.oguzatakan.urlshortner.repository;

import com.oguzatakan.urlshortner.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {

    Optional<ShortUrl> findAllByCode(String code);

}
