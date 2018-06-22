package com.codecool.networking.modes;

import com.codecool.networking.data.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private int serverPort;
    private String serverHost;
    private boolean isStopped;
    private String name;

    public Client(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    @Override
    public void run() {

        System.out.print("* What's your name: ");
        name = new Scanner(System.in).nextLine();
        System.out.println();

        while (!isStopped()) {

            Socket socket;
            try {
                socket = new Socket(serverHost, serverPort);
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Client Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error connecting to server", e);
            }

            try {
                processSendingMessage(socket);
            } catch (Exception e) {
                System.out.println("Error sending message!");
            }
        }
        System.out.println("Client Stopped.");
    }

    private void processSendingMessage(Socket socket) throws Exception {

        try (ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream is = new ObjectInputStream(socket.getInputStream())) {

            while (true) {

                System.out.print("Enter message to send: ");
                String messageString = new Scanner(System.in).nextLine();
                Message message = new Message(messageString, "Sample");
                os.writeObject(message);

            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }
}
