package com.tn.warmup;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class NasaPhoto {

    private static final String NASA_URL =
        "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=16&api_key=DEMO_KEY";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(NASA_URL, JsonNode.class);
        List<String> originUrls = jsonNode.findValuesAsText("img_src")
                                       .stream()
                                       .toList();

        var maxSize = new AtomicLong();
        var maxSizeLocation = new AtomicReference<>();

        
        originUrls.forEach(originUrl -> {
            URI location = restTemplate.headForHeaders(originUrl)
                                       .getLocation();

            long currentSize = restTemplate.headForHeaders(location.toString())
                                             .getContentLength();

            if (currentSize > maxSize.get()) {
                maxSize.set(currentSize);
                maxSizeLocation.set(originUrl);

            }
        });

        System.out.println(maxSizeLocation);
        System.out.println(maxSize);
    }
}
