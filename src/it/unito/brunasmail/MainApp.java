package it.unito.brunasmail;

import it.unito.brunasmail.model.Mail;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {
    private Stage primaryStage;
    private SplitPane rootLayout;

    private List<String> userList;

    public MainApp(){
        userList = new ArrayList<>();
        userList.add("beppe");
        userList.add("stefano");
        userList.add("matteo");
    }

    public boolean userExist(String user){
        for(String s: userList){
            if(s.equals(user))
                return true;
        }
        return false;
    }
    public List<Mail> getUserInbox(String user){
        List<Mail> inbox = new ArrayList<>();
        inbox.add(new Mail("bruno@bruni.it", "Importante", "beppe@brunasmail.it;", 179250540110L, "Ciao beppe"));
        inbox.add(new Mail("bruno@bruni.it", "Importantissima", "beppe@brunasmail.it", 147925042110L, "Ciaoooooo"));
        inbox.add(new Mail("bruno@bruni.it", "Importantissima", "beppe@brunasmail.it; stefano@brunasmail.com;", 247925054010L, "Ciaoooooo"));
        return inbox;
    }

    public void initRootLayout(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ServerRootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Server Brunas Mail");
        initRootLayout();
        new Thread(this::setupServer).start();
    }


    private void setupServer(){
        try {
            ServerSocket s = new ServerSocket(8189);
            System.out.println("Server Connesso al socket");
            while(true){
                Socket incoming = s.accept();
                Runnable r = new serverHandler(this, incoming);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

