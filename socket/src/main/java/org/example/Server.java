package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server.
 *
 * @author Yauheni Halaidzin
 * @since 30.03.2024
 */
public class Server {

    public static void main(String[] args) throws IOException {
//        try (ServerSocket server = new ServerSocket(500)) {
        ServerSocket server = new ServerSocket(5555);
        Socket client = server.accept();

        System.out.println("Connection established");

        InputStreamReader isr = new InputStreamReader(client.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        System.out.println("Message from client: " + br.readLine());

        PrintWriter pw = new PrintWriter(client.getOutputStream());
        pw.print("Hi client!");
        pw.flush();

        System.out.println("Server finished");
    }
}
