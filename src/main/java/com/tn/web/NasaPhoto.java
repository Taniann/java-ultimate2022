package com.tn.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class NasaPhoto {

    private static final String NASA_URL =
        "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=14&api_key=DEMO_KEY";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        JsonNode response = restTemplate.getForObject(NASA_URL, JsonNode.class);

        List<String> imageUrls = response.findValuesAsText("img_src")
                                         .stream()
                                         .toList();

        URI location = restTemplate.headForHeaders(imageUrls.get(0))
                                   .getLocation();

        long firstSize = restTemplate.headForHeaders(location)
                                       .getContentLength();

        AtomicLong minSize = new AtomicLong(firstSize);
        AtomicReference<String> imageUrl = new AtomicReference<>();



/*
        imageUrls.parallelStream()
                 .forEach(originalUrl -> {
                     URI location = restTemplate.headForHeaders(originalUrl)
                                                .getLocation();

                     long currentSize = restTemplate.headForHeaders(location)
                                                    .getContentLength();

                     if ()


                 });
*/



    }
}
