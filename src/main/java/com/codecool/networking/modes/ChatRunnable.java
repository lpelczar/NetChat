package com.codecool.networking.modes;

import java.net.Socket;

public class ChatRunnable implements Runnable {

    private Socket clientSocket;

    ChatRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

    }
}
