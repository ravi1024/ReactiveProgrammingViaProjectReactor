package io.ravi.reactive.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Function;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService= new FluxAndMonoGeneratorService();

    @Test
    void namesFlux()
    {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        //then
        StepVerifier.create(namesFlux)
//                .expectNext("Jai","Shree","Ram")
//                .expectNextCount(3)
                .expectNext("Jai")
                .expectNextCount(2)
                .verifyComplete();


    }

    @Test
    public void namesFlux_map()
    {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("JAI","SHREE","RAM")
                .verifyComplete();
    }

    @Test
    public void namesFlux_immutability(){
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();

        //then
        StepVerifier.create(namesFlux)
//                .expectNext("JAI","SHREE","RAM") //This test will fail, cause Reactive Streams are immutable!
                .expectNext("Jai","Shree","Ram")
                .verifyComplete();
    }

    @Test
    public void nameFlux_filter()
    {
        //given
        int stringLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.nameFlux_filter(stringLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("Shree")
                .verifyComplete();
    }

    @Test
    public void nameFlux_mapAndFilter(){
        //given
        int stringLength = 3;

        //when
        var nameFlux = fluxAndMonoGeneratorService.nameFlux_mapAndFilter(stringLength);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("5-SHREE")
                .verifyComplete();
    }

    @Test
    public void nameFlux_flatMap()
    {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.nameFlux_flatMap(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("S","h","r","e","e")
                .verifyComplete();

    }

    @Test
    public void nameMono_flatMap()
    {
        //given

        //when
        var nameMono = fluxAndMonoGeneratorService.nameMono_flatMap();

        //then
        StepVerifier.create(nameMono)
                .expectNext("JAI")
                .verifyComplete();

    }

    @Test
    public void nameFlux_flatmap_async()
    {
       //given

        //when
        var nameFlux= fluxAndMonoGeneratorService.nameFlux_flatmap_async();

        //then
        StepVerifier.create(nameFlux)
                .expectNextCount(11)
                .verifyComplete();
    }

    @Test
    public void nameFlux_concatMap(){
        //given

        //when
        var nameFlux = fluxAndMonoGeneratorService.nameFlux_concatMap_async();

        //then
        StepVerifier.create(nameFlux)
                .expectNext("J","a","i","S","h","r","e","e","R","a","m")
                .verifyComplete();
    }

    @Test
    public void nameMono()
    {
        //given

        //when
        var nameMono = fluxAndMonoGeneratorService.nameMono();

        //then
        StepVerifier.create(nameMono)
                .expectNext("Jai Bajrangbali")
                .verifyComplete();
    }

    @Test
    public void nameMono_mapAndFilter()
    {
        //given

        //when
        var nameMono = fluxAndMonoGeneratorService.nameMono_mapAndFilter();

        //then
        StepVerifier.create(nameMono)
                .expectNext("6-JORDAN")
                .verifyComplete();
    }


    @Test
    public void nameMono_flatMapV2()
    {
       //given

        //when
        var result = fluxAndMonoGeneratorService.nameMono_flatMapV2();

        //then
        StepVerifier.create(result)
                .expectNext(List.of("R","O","B","E","R","T"))
                .verifyComplete();
    }

    @Test
    public void nameMono_flatMapMany()
    {
       //given

        //when
        var result = fluxAndMonoGeneratorService.nameMono_flatMapMany();

        //then
        StepVerifier.create(result)
                .expectNext("R","O","B","E","R","T")
                .verifyComplete();
    }

    @Test
    public void nameFlux_transform()
    {
        //given

        //when
        var result = fluxAndMonoGeneratorService.nameFlux_transform();

        //then
        StepVerifier.create(result)
                .expectNext("SHREE")
                .verifyComplete();
    }


    @Test
    public void nameFlux_defaultIfEmpty()
    {
        //given
        int strLength = 6;
        //when
        var result = fluxAndMonoGeneratorService.nameFlux_defaultIfEmpty(strLength);

        //then
        StepVerifier.create(result)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void nameFlux_switchIfEmpty()
    {
        //given
        int strLength = 6;
        //when
        var result = fluxAndMonoGeneratorService.nameFlux_switchIfEmpty(strLength);

        //then
        StepVerifier.create(result)
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

}