package com.codecool.networking.modes;

import com.codecool.networking.data.Message;
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
    }

    private void processSendingMessage(Socket socket) throws Exception {

        try (ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {

            MessageListener messageListener = new MessageListener(new ObjectInputStream(socket.getInputStream()));
            new Thread(messageListener).start();

            System.out.println("* Type your message:");

            String messageString = "";

            while (!messageString.equals(".quit!")) {

                messageString = new Scanner(System.in).nextLine();
                if (!messageString.equals(".quit!")) {
                    Message message = new Message(messageString, name);
                    os.writeObject(message);
                }
            }

            stop();
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
    }
}
