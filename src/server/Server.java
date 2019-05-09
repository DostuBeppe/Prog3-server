package it.unito.brunasmail.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;

    public Server(){
    }

    public void initRootLayout(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Server.class.getResource("view/ServerRootLayout.fxml"));
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
        //initRootLayout();
        startServer();
    }

    public void startServer(){
        ServerSocket s = null;
        try {
            s = new ServerSocket(6969);
            while(true){
                Socket incoming = s.accept();
                //Runnable ch = new ConnectionHandler(incoming);
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();
                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream);
                out.println("Hello! This is the ConnectionHandler");
                while(true){
                    String line = in.nextLine();
                    out.println("Echo: "+ line);
                }
                //Thread t = new Thread(ch);
                //t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s!= null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

