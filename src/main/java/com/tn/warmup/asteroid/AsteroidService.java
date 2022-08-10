package com.tn.warmup.asteroid;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AsteroidService {
    private static final String BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed";

    public static void main(String[] args) {
        System.out.println(findBiggestAsteroid());
    }

    private static String findBiggestAsteroid() {
        var url = UriComponentsBuilder.fromUriString(BASE_URL)
                                      .queryParam("api_key", "DEMO_KEY")
                                      .queryParam("start_date", "2022-08-08")
                                      .build()
                                      .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonResponse = restTemplate.getForObject(url, JsonNode.class);

        var nearEarthObjects = jsonResponse.get("near_earth_objects")
                                           .get("2022-08-08");
        Map<String, Double> nameToDiameter = new HashMap<>();
        nearEarthObjects.forEach(jn -> nameToDiameter.put(jn.findValuesAsText("name")
                                                            .get(0), jn.get("estimated_diameter")
                                                                       .get("kilometers")
                                                                       .findValue("estimated_diameter_max")
                                                                       .doubleValue()));

        var maxEntry = nameToDiameter.entrySet()
                                     .stream()
                                     .max(Comparator.comparing(Map.Entry::getValue))
                                     .get();


        return maxEntry.getKey();
    }
}
