package it.unito.brunasmail.model;

import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserList implements Serializable{
    private ObservableList<String> users;

    public UserList(List<String> users) {
        this.users = FXCollections.observableArrayList(users);
    }

    public UserList(){
        users = FXCollections.observableArrayList();
    }


    public void addUser(String user){
        if(!users.contains(user)){
            users.add(user);
        }
    }

    public boolean userExist(String user){
        return users.contains(user);
    }

    public ObservableList<String> getUsers(){ return users; }

    public List<Mail> getUserInbox(String u){
        List<Mail> inbox = new ArrayList<>();
        inbox.add(new Mail("bruno@bruni.it", "Importante", "beppe@brunasmail.it;", 179250540110L, "Ciao beppe"));
        inbox.add(new Mail("bruno@bruni.it", "Importantissima", "beppe@brunasmail.it", 147925042110L, "Ciaoooooo"));
        inbox.add(new Mail("bruno@bruni.it", "Importantissima", "beppe@brunasmail.it; stefano@brunasmail.com;", 247925054010L, "Ciaoooooo"));
        return inbox;
    }
    public List<Mail> getUserOutbox(String u){
        List<Mail> outbox = new ArrayList<>();
        outbox.add(new Mail("bruno@bruni.it", "Importante", "beppe@brunasmail.it;", 179250540110L, "Ciao beppe"));
        outbox.add(new Mail("bruno@bruni.it", "Imporantissima", "beppe@brunasmail.it", 147925042110L, "Ciaoooooo"));
        return outbox;
    }


}
