package com.codecool.networking.modes;

import com.codecool.networking.runnables.MessageListener;
import com.codecool.networking.runnables.ServerMessageSender;

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

        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
        MessageListener messageListener = new MessageListener(is);
        new Thread(messageListener).start();

        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        ServerMessageSender serverMessageSender = new ServerMessageSender(os, name);
        Thread senderThread = new Thread(serverMessageSender);
        senderThread.start();

        while (!isStopped()) {
            if (messageListener.isStopped() || serverMessageSender.isStopped()) {
                messageListener.stop();
                senderThread.interrupt();
                throw new InterruptedException("Client has disconnected!");
            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }
}
