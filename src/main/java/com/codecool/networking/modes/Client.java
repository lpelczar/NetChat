package com.codecool.networking.modes;

import com.codecool.networking.runnables.ClientMessageSender;
import com.codecool.networking.runnables.MessageListener;

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
                System.out.println("* Connected to " + serverHost + ":" + serverPort + "!");
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("* Bye bye!") ;
                    return;
                }
                throw new RuntimeException("* Error connecting to server", e);
            }

            try {
                processSendingMessage(socket);
            } catch (Exception e) {
                System.out.println("* Error sending message!");
            }
        }
        System.out.println("* Bye bye!");
        System.exit(0);
    }

    private void processSendingMessage(Socket socket) throws Exception {

        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        ClientMessageSender messageSender = new ClientMessageSender(os, name);
        new Thread(messageSender).start();

        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        MessageListener messageListener = new MessageListener(is);
        new Thread(messageListener).start();

        while (!isStopped()) {
            if (messageSender.isStopped()) {
                stop();
            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
    }
}
