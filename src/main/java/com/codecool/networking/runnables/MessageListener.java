package com.codecool.networking.runnables;

import com.codecool.networking.data.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageListener implements Runnable {

    private ObjectInputStream is;
    private boolean isStopped;

    public MessageListener(ObjectInputStream is) {
        this.is = is;
    }

    @Override
    public void run() {

        while (!isStopped()) {

            try {
                Message receivedMessage = (Message) is.readObject();
                System.out.println(receivedMessage.getAuthor() + "> " + receivedMessage.getContent());
            } catch (IOException | ClassNotFoundException e) {
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
