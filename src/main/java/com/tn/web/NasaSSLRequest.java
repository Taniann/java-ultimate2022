package com.tn.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Cleanup;
import lombok.SneakyThrows;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class NasaSSLRequest {
    private static final SSLSocketFactory SSL_SOCKET_FACTORY = (SSLSocketFactory) SSLSocketFactory.getDefault();
    private static final String NASA_URL =
        "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=16&api_key=DEMO_KEY";

    @SneakyThrows
    public static void main(String[] args) {
        List<String> originalUrls = findOriginalUrls();
        var maxEntry = getPhotosBySizeMap(originalUrls).entrySet()
                                                       .stream()
                                                       .max(Map.Entry.comparingByKey());
        maxEntry.ifPresent(
            entry -> System.out.println("img_src: " + entry.getValue() + "\n" + "size: " + entry.getKey()));
    }

    @SneakyThrows
    private static List<String> findOriginalUrls() {
        String host = URI.create(NASA_URL)
                         .getHost();
        @Cleanup SSLSocket sslSocket = (SSLSocket) SSL_SOCKET_FACTORY.createSocket(host, 443);
        @Cleanup PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        printWriter.println("GET " + NASA_URL + " HTTP/1.1");
        printWriter.println("Host: " + host);
        printWriter.println("");
        printWriter.flush();

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.equals("0")) {
                break;
            }
            response.append(line);
        }
        String json = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
        String fixedJson = json.replace("1ffa", "");

        return new ObjectMapper().readTree(fixedJson)
                                 .findValuesAsText("img_src");
    }

    private static Map<Long, String> getPhotosBySizeMap(List<String> originalUrls) {
        return originalUrls.stream()
                           .collect(toMap(url -> Long.valueOf(getSize(getLocation(url))), identity()));
    }

    @SneakyThrows
    private static String getLocation(String originalUrl) {
        String host = URI.create(originalUrl)
                         .getHost();
        @Cleanup SSLSocket sslSocket = (SSLSocket) SSL_SOCKET_FACTORY.createSocket(host, 443);
        @Cleanup PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        printWriter.println("HEAD " + originalUrl + " HTTP/1.1");
        printWriter.println("Host: " + host);
        printWriter.println("");
        printWriter.flush();

        return reader.lines()
                     .filter(line -> line.startsWith("Location:"))
                     .map(line -> line.replace("Location: ", ""))
                     .findFirst()
                     .orElseThrow(() -> new IllegalStateException("Location header not present"));
    }

    @SneakyThrows
    private static String getSize(String location) {
        String host = URI.create(location)
                         .getHost();
        @Cleanup SSLSocket sslSocket = (SSLSocket) SSL_SOCKET_FACTORY.createSocket(host, 443);
        @Cleanup PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        printWriter.println("HEAD " + location + " HTTP/1.1");
        printWriter.println("Host: " + host);
        printWriter.println("");
        printWriter.flush();

        return reader.lines()
                     .filter(line -> line.startsWith("Content-Length:"))
                     .map(line -> line.replace("Content-Length: ", ""))
                     .findFirst()
                     .orElseThrow(() -> new IllegalStateException("Content-Length header not present"));
    }
}
