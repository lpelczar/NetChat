package com.codecool.networking;

public class NetChat {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("You need to provide options in following order 'java NetChat mode [address] port':" +
                    "\nmode -> client or server" +
                    "\naddress -> only if you are using client" +
                    "\nport");
            return;
        }
    }
}
