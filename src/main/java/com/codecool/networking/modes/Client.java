package com.codecool.networking.modes;

import com.codecool.networking.data.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private int serverPort;
    private String serverHost;
    private boolean isStopped;

    public Client(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    @Override
    public void run() {

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

    private void processSendingMessage(Socket socket) {

        try (ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream is = new ObjectInputStream(socket.getInputStream())) {

            System.out.print("Enter message to send: ");
            String messageString = new Scanner(System.in).nextLine();
            Message message = new Message(messageString, "Sample");
            os.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }
}
