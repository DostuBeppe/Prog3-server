package it.unito.brunasmail;


import it.unito.brunasmail.model.FileManager;
import it.unito.brunasmail.model.Mail;
import it.unito.brunasmail.model.UserList;
import it.unito.brunasmail.view.ServerRootLayoutController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class serverHandler implements Runnable {
    private MainApp mainApp;
    private Socket incoming;
    private FileManager fileManager;


    public serverHandler(MainApp mainApp, Socket incoming, FileManager fileManager){
        this.mainApp = mainApp;
        this.incoming = incoming;
        this.fileManager = fileManager;
    }

    @Override
    public void run() {
        UserList userList = mainApp.getUserList();
        String log="";
        String user="";
        try {
            ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
            String action = (String)in.readObject();
            if(action.equals("receive")){
                user = (String)in.readObject();
                if(userList.userExist(user)){
                    out.writeObject(fileManager.loadInbox(user));
                    out.writeObject(fileManager.loadOutbox(user));
                } else {
                    out.writeObject(null);
                    out.writeObject(null);
                }
            } else if (action.equals("send")){
                Mail mail = (Mail)in.readObject();
                List<String> receivers = new ArrayList<>(mail.getReceivers());
                for (String receiver : receivers){
                    if (!userList.userExist(receiver)){
                        Mail wrong = new Mail("System","Wrong email address",mail.getSender(),0,
                                "It wasn't possible to send this email to "+receiver+", wrong email address." +
                                        "\n***********************\n"+mail+"\n***********************\nTHIS IS AN AUTOMATED MESSAGE, PLEASE, DO NOT REPLY.");
                        mail.getReceivers().remove(receiver);
                        wrong.setSent(false);
                        fileManager.save(wrong);
                    }
                }
                log = mail.getSender()+" sent an email to "+mail.getReceiversString();
                String finalLog = log;
                Platform.runLater(()->mainApp.addLog(finalLog));
                mail.setSent(true);
                fileManager.save(mail);
            } else if (action.equals("delete")) {
                user = (String) in.readObject();
                Mail mail = (Mail) in.readObject();
                fileManager.delete(mail,user);
                log = user + " deleted this email: " + mail.getMillis();
                String finalLog = log;
                Platform.runLater(() -> mainApp.addLog(finalLog));
            }
            in.close();
            out.close();

        } catch (ClassNotFoundException | IOException e) {
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
