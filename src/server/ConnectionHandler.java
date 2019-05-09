package it.unito.brunasmail.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionHandler implements Runnable{
    private Socket incoming;

    public ConnectionHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        System.out.println("I'm handling a client");
        try{
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();
            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outStream);
            out.println("Hello! This is the ConnectionHandler");

            while(true){
                String line = in.nextLine();
                out.println("Echo: "+ line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
