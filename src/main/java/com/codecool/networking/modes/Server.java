package com.codecool.networking.modes;

import com.codecool.networking.data.Message;
import com.codecool.networking.runnables.MessageListener;
import com.codecool.networking.runnables.MessageSender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {

    private int serverPort;
    private ServerSocket serverSocket;
    private boolean isStopped;
    private String name;

    public Server(int port) {
        this.serverPort = port;
    }

    @Override
    public void run() {
        openServerSocket();

        System.out.print("* What's your name: ");
        name = new Scanner(System.in).nextLine();
        System.out.println();

        while (!isStopped()) {
            System.out.println("* Waiting for a client on port " + serverPort + "...");

            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
                System.out.println("* Client connected! Chat starts...");
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            try {
                processClientRequest(clientSocket);
            } catch (Exception e) {
                System.out.println("\nClient has disconnected!");
            }
        }
        System.out.println("Server Stopped.");
    }

    private void processClientRequest(Socket clientSocket) throws Exception {

        MessageListener messageListener = new MessageListener(new ObjectInputStream(clientSocket.getInputStream()));
        new Thread(messageListener).start();

        MessageSender messageSender = new MessageSender(new ObjectOutputStream(clientSocket.getOutputStream()), name);
        new Thread(messageSender).start();

        while (true) {
            if (messageListener.isStopped() || messageSender.isStopped()) {
                throw new InterruptedException("Client has disconnected!");
            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }
}
