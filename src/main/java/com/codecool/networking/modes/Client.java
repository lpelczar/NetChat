package com.codecool.networking.modes;

import java.net.URL;

public class Client implements Runnable {

    private int serverPort;
    private URL serverUrl;
    private boolean isStopped;
    private Thread runningThread;


    public Client(URL url, int port) {
        this.serverUrl = url;
        this.serverPort = port;
    }

    @Override
    public void run() {

    }
}
