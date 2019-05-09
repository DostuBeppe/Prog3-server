package it.unito.brunasmail.server.view;

import it.unito.brunasmail.server.model.Mail;
import it.unito.brunasmail.server.model.User;
import it.unito.brunasmail.server.Server;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServerRootLayoutController {
    @FXML
    private Server serverApp;
    @FXML
    private ListView<String> userList;
    @FXML
    private ListView<String> logList;

    public void setMainApp(Server serverApp) {
        this.serverApp = serverApp;
    }

    @FXML
    private void addUser(User user){
        userList.getItems().add(user.getEmail() + " has logged in.");
    }

    @FXML
    public void addLog(Mail mail){
        logList.getItems().add(mail.getSender() + " sent an email to" + mail.getReceiversString());
    }
}
