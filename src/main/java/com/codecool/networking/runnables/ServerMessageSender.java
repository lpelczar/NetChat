package com.codecool.networking.runnables;

import com.codecool.networking.data.Message;

import java.io.*;

public class ServerMessageSender implements Runnable {

    private String name;
    private ObjectOutputStream os;
    private boolean isStopped;

    public ServerMessageSender(ObjectOutputStream os, String name) {
        this.name = name;
        this.os = os;
    }

    @Override
    public void run() {

        System.out.println("* Type your message:");

        while (!isStopped()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String input;
                try {

                    do {
                        while (!br.ready()) {
                            Thread.sleep(200);
                        }
                        input = br.readLine();
                    } while ("".equals(input));

                    Message message = new Message(input, name);
                    System.out.println("you> " + input);
                    os.writeObject(message);

                } catch (IOException e) {
                    stop();
                }
            } catch (InterruptedException e) {
                stop();
            }
        }
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
    }
}
