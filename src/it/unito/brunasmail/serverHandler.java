package it.unito.brunasmail;


import it.unito.brunasmail.model.FileManager;
import it.unito.brunasmail.model.Mail;
import it.unito.brunasmail.model.UserList;

import java.io.*;
import java.net.Socket;

public class serverHandler implements Runnable {
    private MainApp mainApp;
    private Socket incoming;

    public serverHandler(MainApp mainApp, Socket incoming){
        this.mainApp = mainApp;
        this.incoming = incoming;
    }

    @Override
    public void run() {
        UserList userList = mainApp.getUserList();
        try {
            ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
            String action = (String)in.readObject();
            if(action.equals("receive")){
                String user = (String)in.readObject();
                if(userList.userExist(user)){
                    for(Mail m: userList.getUserInbox(user)){
                        System.out.println(m.getFormattedDate());
                    }
                    out.writeObject(userList.getUserInbox(user));
                    System.out.println(userList.getUserInbox(user).get(0));
                    out.writeObject(userList.getUserOutbox(user));
                    System.out.println(userList.getUserOutbox(user).get(0));
                } else {
                    out.writeObject(null);
                    out.writeObject(null);
                }
            } else if (action.equals("send")){
                Mail mail = (Mail)in.readObject();
                FileManager.toFile(mail);
                FileManager.readFile("matteo@brunasmail.it");
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
