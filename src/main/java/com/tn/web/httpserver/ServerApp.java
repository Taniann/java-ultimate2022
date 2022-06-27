package com.tn.web.httpserver;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
    private static final int PORT = 8080;
    private static final ExecutorService POOL = Executors.newFixedThreadPool(50);

    @SneakyThrows
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(PORT)) {
            while (true) {
                Socket acceptedSocket = socket.accept();
                POOL.submit(() -> getResponse(acceptedSocket));
            }
        }
    }

    @SneakyThrows
    private static void getResponse(Socket acceptedSocket) {
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));
        @Cleanup PrintWriter writer = new PrintWriter(new OutputStreamWriter(acceptedSocket.getOutputStream()));
        var firstLine = reader.readLine();
        validatePath(firstLine);
        String name = getNameParameter(firstLine);
        String responseMessage = "Hello," + name;

        writer.write(String.format("""
                                        HTTP/1.1 200 OK
                                        Content-Type: text/html
                                        Content-Length: %s
                                       """, responseMessage.length()));
        writer.write("\n");
        writer.write(responseMessage);
        writer.write("\n");
        writer.flush();
        acceptedSocket.close();
    }

    private static void validatePath(String line) {
        String[] requestParts = line.split(" ");
        if (!(isCorrectMethodName(requestParts[0]) && isCorrectMapping(requestParts[1]) && isCorrectHttpVersion(
            requestParts[2]))) {
            throw new IllegalArgumentException("Request is invalid");
        }
    }

    private static boolean isCorrectMethodName(@NonNull String methodName) {
        return methodName.equals("GET");
    }

    private static boolean isCorrectMapping(@NonNull String mappingLine) {
        return mappingLine.startsWith("/hello?name=");
    }

    private static boolean isCorrectHttpVersion(@NonNull String httpVersionLine) {
        return httpVersionLine.contains("HTTP/1.1");
    }

    private static String getNameParameter(String line) {
        String[] requestParts = line.split(" ");
        return requestParts[1].split("=")[1];
    }
}
