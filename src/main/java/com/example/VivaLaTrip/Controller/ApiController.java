package com.example.VivaLaTrip.Controller;

import com.example.VivaLaTrip.Entity.KakaoGeoRes;
import com.example.VivaLaTrip.Form.MapData;
import com.example.VivaLaTrip.Service.MapService;
import com.example.VivaLaTrip.Service.WeatherService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ApiController {

    WeatherService weatherService;
    MapService mapService;

    @Autowired
    public ApiController(WeatherService weatherService, MapService mapService) {
        this.weatherService = weatherService;
        this.mapService = mapService;
    }

    @PostMapping
    public void mapRequest(@RequestBody MapData[] mapData) {
        for (int i = 0; i < mapData.length; i++) {
            log.info("JSOM 값 확인" + mapData[i]);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PostMapping("/kakao")
    public @ResponseBody
    ResponseEntity<?> KakaoApiTest(@RequestParam("keyword") String word) throws UnirestException, UnsupportedEncodingException, JsonProcessingException {

        log.info("컨트롤러 word값 : "+word);

        Map<String,Object> map = mapService.MapParsing(word);

        return ResponseEntity.ok(map);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @ResponseBody
    @GetMapping("/Weather")
    public Map<String, Object> WeatherAPI(@RequestParam("Date") int date) throws IOException, UnirestException {

        log.info("컨트롤러 date값 : "+date);

        Map<String,Object> map = weatherService.WeatherParsing(date);

        return map;
    }

}