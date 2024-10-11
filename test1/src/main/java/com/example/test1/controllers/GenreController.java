package com.example.test1.controllers;

import com.example.test1.commons.Genre;
import com.example.test1.config.CustomProperties;
import com.example.test1.model.Anime;
import com.example.test1.service.GenreService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {

    private Logger logger = LoggerFactory.getLogger(GenreController.class);

    private final Genre actionGenre;
    private Genre anotherActionGenre;
    private final Genre comedyGenre;
    private final Genre horrorGenre;
    private final GenreService genreService;

    private final CustomProperties customProperties;

    @Autowired
    public GenreController(
            @Qualifier("action") Genre actionGenre,
            @Qualifier("comedy") Genre comedyGenre,
            @Qualifier("horror") Genre horrorGenre,
            GenreService genreService, CustomProperties customProperties
    ) {
        this.actionGenre = actionGenre;
        this.comedyGenre = comedyGenre;
        this.horrorGenre = horrorGenre;
        this.genreService = genreService;
        this.customProperties = customProperties;
    }

    @Autowired
    public void setAnotherActionGenre(@Qualifier("action") Genre anotherActionGenre) {
        this.anotherActionGenre = anotherActionGenre;
    }

    @GetMapping("/action")
    public String actionDesc(@RequestHeader("X-APP-ID") String consumeAppId) {
        logger.debug("Received the consume app id: "+ consumeAppId);
        return actionGenre.gereDescription();
    }

    @GetMapping("/comedy")
    public String comedyDesc() {
        return comedyGenre.gereDescription();
    }

    @GetMapping("/genre-checker")
    public String genreChecker() {
        return "Two genre equal? " + (actionGenre == anotherActionGenre);
    }

    @GetMapping("/horror")
    public String horrorDesc() {
        return horrorGenre.gereDescription();
    }

    @GetMapping("/build")
    public String getBuildVersion() {
        return customProperties.getBuildVersion();
    }

    @GetMapping("/java")
    public String getJavaVersion() {
        return customProperties.getJavaVersion();
    }

    @GetMapping("/animes/{genre}")
    public List<Anime> getAnimeByGenre(@PathVariable String genre) {
        logger.debug("method getAnimeByGenre(genre) start");
        List<Anime> animeList = genreService.getAnimeByGenre(genre);
        logger.debug("method getAnimeByGenre(genre) end");
        return animeList;
    }

    @GetMapping("/random-anime/{genre}")
    public Anime getRandomAnimeByGenre(@PathVariable String genre) {
        logger.debug("method getRandomAnimeByGenre(genre) start");
        Anime anime = genreService.randomAnimeGeneratorByGenre(genre);
        logger.debug("method getRandomAnimeByGenre(genre) end");
        return anime;
    }
}
