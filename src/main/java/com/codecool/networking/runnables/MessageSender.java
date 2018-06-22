package com.codecool.networking.runnables;

import com.codecool.networking.data.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class MessageSender implements Runnable {

    private String name;
    private ObjectOutputStream os;
    private boolean isStopped;

    public MessageSender(ObjectOutputStream os, String name) {
        this.name = name;
        this.os = os;
    }

    @Override
    public void run() {

        System.out.println("* Type your message:");

        while (!isStopped()) {

            try {
                String messageString = new Scanner(System.in).nextLine();
                Message message = new Message(messageString, name);
                System.out.println("you> " + messageString);
                os.writeObject(message);
            } catch (IOException e) {
                stop();
            }

        }
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
    }
}
