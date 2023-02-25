package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.exception.MovieException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.Random;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class MockitoMovieReactorService {

    @Mock
    private MovieInfoService movieInfoService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private MovieReactorService movieReactorService;

    @Test
    public void getAllMovies() {
        Mockito.when(movieInfoService.retrieveMoviesFlux()).thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong())).thenCallRealMethod();

        var movies =  movieReactorService.getAllMovies();

        StepVerifier.create(movies)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void getAllMovies_MovieException() {
        Mockito.when(movieInfoService.retrieveMoviesFlux())
                .thenCallRealMethod();

        Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenThrow(new RuntimeException("Something went wrong"));

        var movies =  movieReactorService.getAllMovies();

        StepVerifier.create(movies)
                .expectError(MovieException.class)
                .verify();
    }

    @Test
    void getAllMovies_retry() {
        String errorMsg = "Error occured while fetching movies";
        //given
        Mockito
                .when(movieInfoService.retrieveMoviesFlux())
                .thenCallRealMethod();

        Mockito
                .when(reviewService.retrieveReviewsFlux(anyLong()))
                .thenThrow(new RuntimeException(errorMsg));

        StepVerifier
                .create(movieReactorService.getAllMovies_retry())
                .expectErrorMessage(errorMsg)
                .verify();
    }


}
