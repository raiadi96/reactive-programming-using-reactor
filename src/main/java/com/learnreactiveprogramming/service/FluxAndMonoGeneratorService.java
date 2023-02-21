package com.learnreactiveprogramming.service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Slf4j
public class FluxAndMonoGeneratorService {
    public Flux<String> generateStringFlux(){
        return Flux.fromIterable(List.of("Aditya","Akshat", "Chloe", "Zen")).filter(name -> name.length()>3).log();
    }

    public Flux<String> generateStringFlatMap(){
        return Flux.fromIterable(List.of("Aditya","Akshat", "Chloe", "Zen")).filter(name -> name.length()>3).flatMap(name -> splitName(name)).log();
    }

    public Flux<String> concatStringFlux(){
        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");
        return Flux.concat(flux1,flux2).log();
    }

    public Flux<String> generateStringAsyncFlatMap(){
        return Flux.fromIterable(List.of("Aditya","Akshat", "Chloe", "Zen")).filter(name -> name.length()>3).flatMap(name -> splitNameWithDelay(name)).log();
    }

    public Flux<String> generateFluxWithMergeSequential(){
        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");
        return Flux.mergeSequential(flux1,flux2).log();
    }

    public Flux<Integer> generateFluxWithZipUsingAddition(){
        Flux<Integer> flux1 = Flux.just(1,2,3);
        Flux<Integer> flux2 = Flux.just(4,5,6);
        return Flux.zip(flux1,flux2,Integer::sum).log();
    }

    public Flux<Integer> createFluxException(){
       return  Flux.just(1,2,3).concatWith( Flux.error(new RuntimeException("An Exception Occured @ "))).concatWith(Flux.just(4)).log();
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

    public Flux<String> doOnErrorExample(){
        List<String> alphabets = List.of("A", "B", "C");
        return Flux.fromIterable(alphabets)
                .concatWith(Flux.error(new RuntimeException("An Exception Occured")))
                .doOnError(e -> log.error("Error Occured",e))
                .log();
    }

    public Flux<String> transform_fluxString(){
        Function<Flux<String>, Flux<String>> filterMap = name -> name.filter(n -> n.length() >3).map(s -> s.toUpperCase());
        return Flux.fromIterable(List.of("Aditya","Akshat","Chloe","Zen"))
                .transform(filterMap)
                .flatMap(name -> splitName(name))
                .log();
    }

    public Flux<String> exploreOnErrorReturn(){
        return Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("An Exception Occured @ ")))
                .onErrorReturn("Error")
                .concatWith(Flux.just("D","E","F"))
                .log();
    }

    public Flux<String>  exploreOnErrorResume(){
         Flux<String> secondFlux = Flux.just("D","E","F");
         return Flux.just("A","B","C")
                 .concatWith(Flux.error(new RuntimeException("An Exception Occured @ ")))
                 .onErrorResume(e -> secondFlux)
                 .concatWith(Flux.just("G","H"))
                 .log();
    }

    public Flux<String> exploreOnErrorContinue(){
        return Flux.just("A","B","C")
                .map(o ->
                {
                    if(o.equalsIgnoreCase("B")){
                        throw new RuntimeException("An Exception Occured @ ");
                    }
                    return o;
                })
                .onErrorContinue((e,name) -> {
                    log.error(e.getMessage());
                    log.info((String)name);
                });
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
