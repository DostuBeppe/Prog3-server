package it.unito.brunasmail;

import it.unito.brunasmail.model.Mail;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class serverHandler implements Runnable {
    private MainApp mainApp;
    private Socket incoming;

    public serverHandler(MainApp mainApp, Socket incoming){
        this.mainApp = mainApp;
        this.incoming = incoming;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
            String action = (String)in.readObject();
            if(action.equals("out")){
                String user = (String)in.readObject();
                if(mainApp.userExist(user)){
                    out.writeObject(new ArrayList<Mail>(mainApp.getUserInbox(user)));
                } else {
                    out.writeObject(null);
                }
            }
            out.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                incoming.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
