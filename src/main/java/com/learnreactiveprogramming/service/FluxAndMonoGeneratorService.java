package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {
    public Flux<String> generateStringFlux(){
        return Flux.fromIterable(List.of("Aditya","Akshat", "Chloe", "Zen")).filter(name -> name.length()>3).log();
    }

    public Flux<String> generateStringFlatMap(){
        return Flux.fromIterable(List.of("Aditya","Akshat", "Chloe", "Zen")).filter(name -> name.length()>3).flatMap(name -> splitName(name)).log();
    }

    public Flux<String> generateStringAsyncFlatMap(){
        return Flux.fromIterable(List.of("Aditya","Akshat", "Chloe", "Zen")).filter(name -> name.length()>3).flatMap(name -> splitNameWithDelay(name)).log();
    }

    public Flux<String> generateStringConcatMap(){
        return Flux.fromIterable(List.of("Aditya","Akshat","Chloe","Zen")).filter(name -> name.length()>3).concatMap(name -> splitNameWithDelay(name)).log();
    }

    private Flux<String> splitName(String name) {
        return Flux.fromIterable(List.of(name.split("")));
    }

    private Flux<String> splitNameWithDelay(String name){
        return Flux.fromIterable(List.of(name.split(""))).delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    }

    public Mono<String> generateMonoString(){
        return Mono.just("Aditya").log();
    }
    public Mono<List<String>> generateMonoFlatMapString(){
        return Mono.just("Aditya").flatMap(this::splitStringAndReturnMonoList).log();
    }

    public Flux<String> transform_fluxString(){
        Function<Flux<String>, Flux<String>> filterMap = name -> name.filter(n -> n.length() >3).map(s -> s.toUpperCase());
        return Flux.fromIterable(List.of("Aditya","Akshat","Chloe","Zen"))
                .transform(filterMap)
                .flatMap(name -> splitName(name))
                .log();
    }

    private Mono<List<String>> splitStringAndReturnMonoList(String s) {
        return Mono.just(List.of(s.split("")));
    }

    public Flux<String> generateStringFlux_map(){
        return generateStringFlux().map(String::toUpperCase).log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.generateStringFlux()
                .subscribe(name -> System.out.println("Flux: "+name));

        fluxAndMonoGeneratorService.generateMonoString()
                .subscribe(name -> System.out.println("Mono: "+name));
        fluxAndMonoGeneratorService.generateStringFlux_map()
                .subscribe(name -> System.out.println("Upper Case Flux: "+name));
    }

}
