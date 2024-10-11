package com.example.test1.service.client;

import com.example.test1.model.Anime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "test2")
public interface AnimeClient {
    @GetMapping(value = "/animes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Anime> animeList();
}
