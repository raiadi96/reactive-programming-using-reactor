package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

    @Test
    void generateStringFlux() {
        StepVerifier.create(service.generateStringFlux())
                .expectNext("Aditya")
                .expectNext("Akshat")
                .expectNext("Chloe")
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void transformStringFlux() {
        StepVerifier.create(service.transform_fluxString())
                .expectNextCount(17)
                .verifyComplete();
    }
    @Test
    void generateStringFlatMap() {
        StepVerifier.create(service.generateStringFlatMap())
                .expectNextCount(17)
                .verifyComplete();
    }
    @Test
    void generateMonoString() {
        StepVerifier.create(service.generateMonoString())
                .expectNext("Aditya")
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void generateStringFlux_map() {
        StepVerifier.create(service.generateStringFlux_map())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void generateStringAsyncFlatMap() {
        StepVerifier.create(service.generateStringAsyncFlatMap())
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void generateStringConcatMap() {
        StepVerifier.create(service.generateStringConcatMap())
                .expectNextCount(17)
                .verifyComplete();}

    @Test
    void generateMonoFlatMapString() {
        StepVerifier.create(service.generateMonoFlatMapString()).expectNextCount(1).verifyComplete();
    }
}
