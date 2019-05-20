package it.unito.brunasmail;

import it.unito.brunasmail.model.UserList;
import it.unito.brunasmail.view.ServerRootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp extends Application {
    private Stage primaryStage;
    private SplitPane rootLayout;

    private UserList userList;
    private ObservableList<String> logList = FXCollections.observableArrayList();

    public ObservableList<String> getLogList() {
        return logList;
    }

    public void addLog(String log){
        logList.add(log);
    }

    public UserList getUserList(){ return userList; }

    public MainApp(){
        userList = new UserList();
        userList.addUser("stefano@brunasmail.it");
        userList.addUser("matteo@brunasmail.it");
        userList.addUser("beppe@brunasmail.it");
    }


    public void initRootLayout(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ServerRootLayout.fxml"));
            rootLayout = loader.load();
            ServerRootLayoutController controller = loader.getController();
            controller.setMainApp(this);
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
        Thread t = new Thread(this::setupServer);
        t.setDaemon(true);
        t.start();
    }


    private void setupServer(){
        try {
            ServerSocket s = new ServerSocket(8189);
            System.out.println("Server Connesso al socket");
            while(true){
                Socket incoming = s.accept();
                Runnable r = new ServerHandler(this, incoming, new FileManager());
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

