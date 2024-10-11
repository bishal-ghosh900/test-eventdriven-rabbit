package com.example.test1.service;

import com.example.test1.model.Anime;
import com.example.test1.service.client.AnimeClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreService {

    private final AnimeClient animeClient;
    private final StreamBridge streamBridge;
    private final Logger log = LoggerFactory.getLogger(GenreService.class);

    public List<Anime> getAnimeByGenre(String genre) {
        List<Anime> anime = animeClient.animeList();

        return anime
                .stream()
                .filter(m -> m.genre().equalsIgnoreCase(genre))
                .toList();
    }

    public Anime randomAnimeGeneratorByGenre(String genre) {
        List<Anime> animeList = animeClient.animeList();
        Random ran = new Random();
        List<Anime> filteredAnime = animeList.stream()
                .filter(a -> a.genre().equalsIgnoreCase(genre))
                .toList();
        Anime anime = filteredAnime.get(ran.nextInt(filteredAnime.size()));
        log.info("sending anime details to the message microservice for the anime " + anime);
        boolean triggered = streamBridge.send("mesmicro-out-0", anime);
        log.info("triggered sending anime details to the message microservice: " + triggered);
        return anime;
    }
}
