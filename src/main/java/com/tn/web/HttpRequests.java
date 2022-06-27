package com.tn.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public class HttpRequests {
    private static final String NASA_URL =
        "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=DEMO_KEY";

    public static void main(String[] args) {
        printMaxSizeImageUriUsingRestTemplateSequentially();
        printMaxSizeImageUriUsingRestTemplateSInParallel();
    }

    private static void printMaxSizeImageUriUsingRestTemplateSequentially() {
        Long startTime = System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(NASA_URL, JsonNode.class);

        AtomicLong maxSize = new AtomicLong();
        AtomicReference<String> imgSrc = new AtomicReference<>();

        List<String> imageUrls = jsonNode.get("photos")
                                         .findValuesAsText("img_src")
                                         .stream()
                                         .toList();
        imageUrls.forEach(originalUri -> {
            HttpHeaders httpHeaders = restTemplate.headForHeaders(originalUri);

            URI currentImageURI = requireNonNull(httpHeaders.getLocation());

            HttpHeaders locationHttpHeaders = restTemplate.headForHeaders(currentImageURI);

            long currentSize = locationHttpHeaders.getContentLength();
            if (currentSize > maxSize.get()) {
                maxSize.set(currentSize);
                imgSrc.set(originalUri);
            }

        });
        Long executionTime = System.currentTimeMillis() - startTime;

        System.out.println("img_src: " + imgSrc.get());
        System.out.println("size: " + maxSize);
        System.out.println("execution_time: " + executionTime);
    }

    private static void printMaxSizeImageUriUsingRestTemplateSInParallel() {
        Long startTime = System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(NASA_URL, JsonNode.class);

        AtomicLong maxSize = new AtomicLong();
        AtomicReference<String> imgSrc = new AtomicReference<>();

        List<String> imageUrls = jsonNode.get("photos")
                                         .findValuesAsText("img_src")
                                         .stream()
                                         .toList();
        imageUrls.parallelStream()
                 .forEach(originalUri -> {
                     HttpHeaders httpHeaders = restTemplate.headForHeaders(originalUri);

                     URI currentImageURI = requireNonNull(httpHeaders.getLocation());

                     HttpHeaders locationHttpHeaders = restTemplate.headForHeaders(currentImageURI);

                     long currentSize = locationHttpHeaders.getContentLength();
                     if (currentSize > maxSize.get()) {
                         maxSize.set(currentSize);
                         imgSrc.set(originalUri);
                     }

                 });
        Long executionTime = System.currentTimeMillis() - startTime;

        System.out.println("img_src: " + imgSrc.get());
        System.out.println("size: " + maxSize);
        System.out.println("execution_time: " + executionTime);
    }

    @SneakyThrows
    private static void printMaxSizeImageUriUsingHttpClientSequentially() {
        var startTime = System.currentTimeMillis();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                                             .uri(URI.create(NASA_URL))
                                             .GET()
                                             .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> imageUrls = objectMapper.readTree(response.body())
                                           .findValuesAsText("img_src")
                                           .stream()
                                           .toList();

        AtomicLong maxSize = new AtomicLong();
        AtomicReference<String> imgSrc = new AtomicReference<>();

        imageUrls.forEach(originalUri -> {
            
        });


    }

}
