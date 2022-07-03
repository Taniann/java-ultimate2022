package com.tn.warmup;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketPostRequest {

    private static final String jsonBody = """
        {
        "user": {
            "firstName": "Tetiana",
            "lastName": "Nebesna"
          },
          "picture": {
            "url": "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00016/opgs/edr/ncam/NLA_398919784EDR_F0030078NCAM00301M_.JPG",
            "size": 743518
          }
        }
        """;

    @SneakyThrows
    public static void main(String[] args) {
        try (Socket socket = new Socket("93.175.204.87", 8080)) {

            @Cleanup PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.println("POST /pictures HTTP/1.1");
            writer.println("Host: 93.175.204.87");
            writer.println("Content-Type: application/json");
            writer.println(String.format("Content-Length: %s", jsonBody.length()));

            writer.println("");
            writer.println(jsonBody);
            writer.println("");
            writer.flush();
        }
    }
}
