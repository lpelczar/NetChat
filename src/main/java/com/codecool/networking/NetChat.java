package com.codecool.networking;

import com.codecool.networking.modes.Client;
import com.codecool.networking.modes.Server;
import com.codecool.networking.utils.IntegerChecker;


public class NetChat {

    public static void main(String[] args) {

        if (args.length == 0) {
            printConsoleArgumentsInformation();
            return;
        }

        switch (args[0]) {
            case "server":
                handleStartingServer(args);
                break;
            case "client":
                handleStartingClient(args);
                break;
            default:
                System.out.println("Invalid mode input: use 'server' or 'client'");
        }
    }

    private static void printConsoleArgumentsInformation() {
        System.out.println("You need to provide options in following order 'java NetChat mode [address] port':" +
                "\nmode -> client or server" +
                "\naddress -> only if you are using client" +
                "\nport");
    }

    private static void handleStartingServer(String[] args) {

        if (args.length != 2) {
            printConsoleArgumentsInformation();
            return;
        }

        String port = args[1];
        if (!IntegerChecker.isInteger(port)) {
            printConsoleArgumentsInformation();
            return;
        }

        Server server = new Server(Integer.parseInt(port));
        new Thread(server).start();
    }

    private static void handleStartingClient(String[] args) {

        if (args.length != 3) {
            printConsoleArgumentsInformation();
            return;
        }

        String port = args[2];
        if (!IntegerChecker.isInteger(port)) {
            printConsoleArgumentsInformation();
            return;
        }

        String host = args[1];

        Client client = new Client(host, Integer.parseInt(port));
        new Thread(client).start();
    }
}
