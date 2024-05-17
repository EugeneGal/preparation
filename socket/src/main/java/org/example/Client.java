package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client.
 *
 * @author Yauheni Halaidzin
 * @since 30.03.2024
 */
public class Client {

    public static void main(String[] args) throws IOException {
//        try (Socket socket = new Socket("localhost", 500)) {
        Socket socket = new Socket("127.0.0.1", 5555);
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.print("Hi server!");
        pw.flush();

        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        System.out.println("Message from server: " + br.readLine());

        System.out.println("Client finished");
    }

}
