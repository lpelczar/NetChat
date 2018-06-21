package com.codecool.networking.modes;

import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private int serverPort;
    private String serverHost;
    private Socket socket;
    private boolean isStopped;
    private Thread runningThread;


    public Client(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    @Override
    public void run() {

        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        openClientSocket();

        while (!isStopped()) {

            try {
                processSendingMessage();
            } catch (Exception e) {
                System.out.println("Error sending message!");
            }
        }
        System.out.println("Server Stopped.");
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing client", e);
        }
    }

    private void openClientSocket() {
        try {
            this.socket = new Socket(serverHost, serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open socket on host: " + serverHost + " and port: " + serverPort, e);
        }
    }
}
