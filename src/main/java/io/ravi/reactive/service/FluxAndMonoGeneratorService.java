package io.ravi.reactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){

        return Flux.fromIterable(Arrays.asList("Jai","Shree","Ram")).log();
    }

    public Flux<String> namesFlux_map()
    {
        return Flux.fromIterable(Arrays.asList("Jai","Shree","Ram"))
                .map(String::toUpperCase)
//                .map(name -> name.toUpperCase())
                .log();
    }

    public Flux<String> namesFlux_immutability()
    {
        var nameFlux = Flux.fromIterable(Arrays.asList("Jai","Shree","Ram"));
        nameFlux.map(String::toUpperCase);
        return nameFlux;
    }

    public Flux<String> nameFlux_filter(int stringLength)
    {
        return Flux.fromIterable(Arrays.asList("Jai","Shree","Ram"))
                .filter(name -> name.length() > stringLength);
    }

    public Flux<String> nameFlux_mapAndFilter(int stringLength)
    {
        return Flux.fromIterable(Arrays.asList("Jai","Shree","Ram"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
                .map(name -> name.length() + "-"+ name)
                .log();

    }

    public Flux<String> nameFlux_flatMap(int stringLength)
    {
        return Flux.fromIterable(Arrays.asList("Jai","Shree","Ram"))
                .filter(name -> name.length() > stringLength)
                .flatMap(name -> splitStringReturnFlux(name))
                .log();

    }

    public Mono<String> nameMono_flatMap()
    {
        return Mono.just("Jai")
                .map(String::toUpperCase)
                .flatMap(name -> splitStringReturnsMono(name))
                .log();

    }

    public Flux<String> splitStringReturnFlux(String s)
    {
        //return Flux.fromArray(new String[]{s}); //coverting given string into an array of string inside fromArray() method
        return Flux.fromArray(s.split(""));

    }

    public Mono<String> splitStringReturnsMono(String s)
    {
        return Mono.just(s); //  fromArray(s.split(""));
    }

    public Flux<String> nameFlux_flatmap_async()
    {
        return Flux.fromIterable(List.of("Jai","Shree","Ram"))
                .flatMap(name -> spillStringReturnsFlux_asyn(name))
                .log();
    }

    public Flux<String> spillStringReturnsFlux_asyn(String s)
    {
        var randomInt = new Random().nextInt(1000);
        return Flux.fromArray(s.split(""))
                .delayElements(Duration.ofMillis(randomInt));
    }

    public Flux<String> nameFlux_concatMap_async(){
        return Flux.fromIterable(List.of("Jai","Shree","Ram"))
                .concatMap(name -> spillStringReturnsFlux_asyn(name))
                .log();
    }

    public Flux<String> nameFlux_transform()
    {
        Function<Flux<String>,Flux<String>> function = name -> name.map(String::toUpperCase)
                                                                    .filter(s -> s.length() > 3);

        return Flux.fromIterable(List.of("Jai","Shree","Ram"))
                .transform(function)
                .log();
    }

    public Flux<String> nameFlux_defaultIfEmpty(int strLength)
    {
        Function<Flux<String>,Flux<String>> function = name -> name.map(String::toUpperCase)
                            .filter(s -> s.length() > strLength)
                            //.flatMap(name -> splitStringReturnFlux(name))
                            .flatMap(this::splitStringReturnFlux);

        return Flux.fromIterable(List.of("Jai","Shree","Ram"))
                .transform(function)
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> nameFlux_switchIfEmpty(int strLength)
    {
        Function<Flux<String>,Flux<String>> function = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > strLength)
                //.flatMap(name -> splitStringReturnFlux(name))
                .flatMap(this::splitStringReturnFlux);

        var defaultFlux = Flux.just("default").transform(function);

        return Flux.fromIterable(List.of("Jai","Shree","Ram"))
                .transform(function)
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Mono<String> nameMono_mapAndFilter() {
        return Mono.just("Jordan")
                .map(String::toUpperCase)
                .filter(name -> name.length() > 3)
                .map(name -> name.length() + "-" + name)
                .log();
    }

    public Mono<List<String>> nameMono_flatMapV2()
    {
        return Mono.just("Robert")
                .map(String::toUpperCase)
//                .flatMap(name -> splitStringReturnsMonoV2(name))
                .flatMap(this::splitStringReturnsMonoV2)
                .log();
    }

    private Mono<List<String>> splitStringReturnsMonoV2(String s) {
        var charArray = s.split("");
        return Mono.just(List.of(charArray));
    }

    public Flux<String> nameMono_flatMapMany()
    {
        return Mono.just("Robert")
                .map(String::toUpperCase)
//                .flatMap(name -> splitStringReturnFlux(name)) //below is alternative way of this lambda function
                .flatMapMany(this::splitStringReturnFlux)
                .log();
    }

    public Mono<String> nameMono(){
        return Mono.just("Jai Bajrangbali").log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

//        fluxAndMonoGeneratorService.namesFlux().subscribe(name -> System.out.println("Name is: "+name));

//        fluxAndMonoGeneratorService.nameMono().subscribe(name -> System.out.println("Mono name is: " +name));

        fluxAndMonoGeneratorService.nameFlux_flatMap(2).subscribe(name -> System.out.println("Mono name is: " +name));
    }
}
