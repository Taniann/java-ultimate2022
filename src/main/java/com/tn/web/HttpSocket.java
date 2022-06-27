package com.tn.web;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpSocket {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(50);
    private static final String jsonMessage = """
        {
                     "firstName": "Tetiana",
                     "lastName": "Nebesna",
                     "city": "Kyiv",
                     "hobby": "Hiking, Yoga",
                     "team": "Petros",
                     "role": "Participant",
                     "roommate": "Nadya Hainutsa",
                     "roommatesFavoriteDish": "Avocado toast"
                   }
                                   """;

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup ServerSocket serverSocket = new ServerSocket(8282);
        while (true) {
            Socket socket = serverSocket.accept();
            POOL.execute(() -> doConnection(socket));
        }
    }



    @SneakyThrows
    private static void doConnection(Socket socket) {
        @Cleanup PrintWriter writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
        writer.write(String.format("""
                                       HTTP/1.1 200 OK
                                       Host:  172.16.11.7
                                       Content-Type: application/json
                                       Content-Length: %s
                                       Next: http://172.16.10.161:9090/
                                       """, jsonMessage.length()));
        writer.write("\r\n");
        writer.println(jsonMessage);
        writer.write("\r\n");
        writer.flush();
        socket.close();
    }
}
