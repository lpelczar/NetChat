package com.codecool.networking.modes;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {

    private int serverPort = 9000;
    private ServerSocket serverSocket;
    private boolean isStopped = false;
    private Thread runningThread;

    Server(int port) {
        this.serverPort = port;
    }

    @Override
    public void run() {

        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        openServerSocket();

        
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }
}
