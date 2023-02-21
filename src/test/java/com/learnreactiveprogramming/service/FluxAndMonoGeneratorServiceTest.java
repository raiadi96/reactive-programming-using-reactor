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
    void concatStringFlux(){
        StepVerifier.create(service.concatStringFlux())
                .expectNext("A")
                .expectNext("B")
                .expectNext("C")
                .expectNext("D")
                .expectNext("E")
                .expectNext("F")
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

    @Test
    void generateFluxWithMergeSequential() {
        StepVerifier.create(service.generateFluxWithMergeSequential())
                .expectNext("A")
                .expectNext("B")
                .expectNext("C")
                .expectNext("D")
                .expectNext("E")
                .expectNext("F")
                .expectNextCount(0)
                .verifyComplete();}

    @Test
    void generateFluxWithZipUsingAddition() {
        StepVerifier.create(service.generateFluxWithZipUsingAddition())
                .expectNext(5)
                .expectNext(7)
                .expectNext(9)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void createFluxException() {
        StepVerifier.create(service.createFluxException())
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void exploreOnErrorReturn() {
        StepVerifier.create(service.exploreOnErrorReturn())
                .expectNext("A","B", "C")
                .expectNext("Error")
                .expectNext("D")
                .expectNext("E")
                .expectNext("F")
                .verifyComplete();

    }

    @Test
    void exploreOnErrorResume() {
        StepVerifier.create(service.exploreOnErrorResume())
                .expectNext("A","B", "C")
                .expectNext("D")
                .expectNext("E")
                .expectNext("F")
                .expectNext("G","H")
                .verifyComplete();}

    @Test
    void doOnErrorExample() {
        StepVerifier.create(service.doOnErrorExample())
                .expectNext("A","B", "C")
                .expectError(RuntimeException.class)
                .verify();}
    }


