package com.codecool.networking.runnables;

import com.codecool.networking.data.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientMessageSender implements Runnable {

    private String name;
    private ObjectOutputStream os;
    private boolean isStopped;

    public ClientMessageSender(ObjectOutputStream os, String name) {
        this.name = name;
        this.os = os;
    }

    @Override
    public void run() {
        System.out.println("* Type your message:");

        while (!isStopped()) {
            String messageString = "";
            while (!messageString.equals(".quit!")) {

                messageString = new Scanner(System.in).nextLine();
                if (!messageString.equals(".quit!")) {
                    Message message = new Message(messageString, name);
                    try {
                        os.writeObject(message);
                    } catch (IOException e) {
                        stop();
                    }
                }
            }
            stop();
        }
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
    }
}
