package it.unito.brunasmail.view;

import it.unito.brunasmail.MainApp;
import it.unito.brunasmail.model.Mail;
import it.unito.brunasmail.model.UserList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServerRootLayoutController {
    private MainApp mainApp;
    @FXML
    private ListView<String> userList;
    @FXML
    private ListView<String> logList;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        userList.setItems(mainApp.getUserList().getUsers());

    }

    public ServerRootLayoutController(){}

    @FXML
    private void initialize() {
    }

}
