package com.tn.web;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {

    @SneakyThrows
    public static void main(String[] args) {
/*        try (Socket clientSocket = new Socket("93.175.204.87", 8899)) {
            OutputStream outputStream = clientSocket.getOutputStream();
            var writer = new PrintWriter(new OutputStreamWriter(outputStream));
            writer.println("Hello from Tetiana Nebesna (Kyiv)");
            writer.flush();
        }*/

        try (Socket clientSocket = new Socket("93.175.204.87", 8080)) {
            OutputStream outputStream = clientSocket.getOutputStream();
            var writer = new PrintWriter(new OutputStreamWriter(outputStream));

            writer.println("GET /hello?name=TetianaNebesna HTTP/1.1");
            writer.println("Host:  93.175.204.87");
            writer.println();
            writer.flush();


            InputStream inputStream = clientSocket.getInputStream();
        }
    }
}
