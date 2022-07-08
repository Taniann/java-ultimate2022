package com.tn.warmup;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;

public class RestTemplateRequest {
    private static final String URL = "http://93.175.204.87:8080/users";

    @SneakyThrows
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        User user = new User();

        user.setFirstName("Tetiana");
        user.setLastName("Nebesna");

        restTemplate.postForObject(URL, user, User.class);


/*        HttpRequest httpRequest = HttpRequest.newBuilder()
                                             .uri(URI.create(URL))
                                             .POST(HttpRequest.BodyPublishers.ofString(json))
                                             .header("Content-Type", "application/json")
                                             .build();
        HttpClient httpClient = HttpClient.newBuilder()
                                          .build();

        httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());*/
    }

    @Data
    @NoArgsConstructor
    static class User {
        private String firstName;
        private String lastName;
    }
}
