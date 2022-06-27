package com.tn.web.httpserver;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientApp {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    @SneakyThrows
    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {

            @Cleanup PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.write("GET /hello?name=Bob HTTP/1.1");
            writer.write(HOST);
            writer.write("\n");
            writer.flush();
            String response;
            while ((response = reader.readLine()) != null) {
                System.out.println(response);
            }
        }
    }
}
