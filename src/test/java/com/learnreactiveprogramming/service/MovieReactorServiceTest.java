package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import com.learnreactiveprogramming.domain.MovieInfo;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class MovieReactorServiceTest {

    MovieInfoService movieInfoService = new MovieInfoService();
    ReviewService reviewService = new ReviewService();
    MovieReactorService movieReactorService = new MovieReactorService(movieInfoService, reviewService);
    @Test
    void getAllMovies() {
        Flux<Movie> movieInfoFlux = movieReactorService.getAllMovies();
        StepVerifier.create(movieInfoFlux).assertNext(movie -> {
            MovieInfo movieInfo = movie.getMovie();
            assertNotNull(movieInfo.getMovieInfoId());
            assertNotNull(movieInfo.getName());
            assertNotNull(movieInfo.getYear());
            assertFalse(movie.getReviewList().isEmpty());
        }).assertNext(movie -> {
            MovieInfo movieInfo = movie.getMovie();
            assertNotNull(movieInfo.getMovieInfoId());
            assertNotNull(movieInfo.getName());
            assertNotNull(movieInfo.getYear());
            assertFalse(movie.getReviewList().isEmpty());
        }).assertNext(movie -> {
            MovieInfo movieInfo = movie.getMovie();
            assertNotNull(movieInfo.getMovieInfoId());
            assertNotNull(movieInfo.getName());
            assertNotNull(movieInfo.getYear());
            assertFalse(movie.getReviewList().isEmpty());
        }).verifyComplete();
    }

    @Test
    void getMovieById() {
        Mono<Movie> movieInfoFlux = movieReactorService.getMovieById(100L);
        StepVerifier.create(movieInfoFlux).assertNext(movie -> {
            MovieInfo movieInfo = movie.getMovie();
            assertNotNull(movieInfo.getMovieInfoId());
            assertNotNull(movieInfo.getName());
            assertNotNull(movieInfo.getYear());
            assertFalse(movie.getReviewList().isEmpty());
        }).verifyComplete();
    }
}