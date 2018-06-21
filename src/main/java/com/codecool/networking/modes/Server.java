package com.codecool.networking.modes;

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

    }
}
