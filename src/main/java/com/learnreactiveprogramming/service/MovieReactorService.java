package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import com.learnreactiveprogramming.domain.MovieInfo;
import com.learnreactiveprogramming.exception.MovieException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class MovieReactorService {
    private final MovieInfoService movieInfoService;
    private final ReviewService reviewService;

    public MovieReactorService(MovieInfoService movieInfoService, ReviewService reviewService) {
        this.movieInfoService = movieInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Movie> getAllMovies() {
        Flux<MovieInfo> allMoviesFlux = this.movieInfoService.retrieveMoviesFlux();

        return allMoviesFlux.flatMap(movieInfo -> {
            var reviews = this.reviewService.retrieveReviewsFlux(movieInfo.getMovieInfoId()).collectList();
            return reviews.map(review -> new Movie(movieInfo, review));
        }).onErrorMap(ex ->{
            log.error("Error retrieving reviews "+ ex.getMessage());
            return  new MovieException(ex.getMessage());
        });
    }

    public Flux<Movie> getAllMovies_retry(){
        Flux<MovieInfo> allMoviesFlux = this.movieInfoService.retrieveMoviesFlux();
        return allMoviesFlux.flatMap(movieInfo -> {
            var reviews = this.reviewService.retrieveReviewsFlux(movieInfo.getMovieInfoId()).collectList();
            return reviews.map(review -> new Movie(movieInfo, review));
        }).onErrorMap(ex ->{
            log.error("Error retrieving reviews "+ ex.getMessage());
            return  new MovieException(ex.getMessage());
        }).retry().log();
    }

    public Mono<Movie> getMovieById(@NonNull Long movieId) {

        MovieInfo movieInfo =  this.movieInfoService.retrieveMovieUsingId(movieId);
        var review = this.reviewService.retrieveReviews(movieInfo.getMovieInfoId());
        return Mono.just(new Movie(movieInfo, review));
        }
    }
