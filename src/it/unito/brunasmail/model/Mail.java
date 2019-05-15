package it.unito.brunasmail.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mail implements Serializable {
    private transient StringProperty sender;
    private transient StringProperty subject;
    private transient ListProperty<String> receivers;
    private transient ObjectProperty<LocalDateTime> date;
    private transient StringProperty message;
    // private Boolean viewed;

    public Mail(String sender, String subject, String receivers, long timestamp, String message) {
        this.sender = new SimpleStringProperty(sender);
        this.subject = new SimpleStringProperty(subject);
        this.receivers= new SimpleListProperty<>();
        if (receivers != null) setReceivers(receivers);
        this.date = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId()));
        this.message = new SimpleStringProperty(message);
        //this.viewed = viewed;
    }

    public Mail(){
        init();
    }

    public String getSender() {
        return sender.get();
    }

    public StringProperty senderProperty() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender.set(sender);
    }


    public String getSubject() {
        return subject.get();
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void removeFromReceivers(String mail) {
        ArrayList<String> tmp = new ArrayList<>(receivers);
        tmp.remove(mail);
        receivers.set(FXCollections.observableArrayList(tmp));
    }

    public void setReceivers(String r) {
        ArrayList<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(r);
        while (m.find()) {
            list.add(m.group());
        }
        //String[] array = r.split("\\\\s*;\\\\s*",-1);
        receivers.set(FXCollections.observableArrayList(list));
    }

    public StringProperty receiversStringProperty() {
        if (receivers == null) {
            return new SimpleStringProperty("");
        }
        StringBuilder str = new StringBuilder();
        for (String s : receivers) {
            System.out.println("Dest: " + s);
            str.append(s).append("; ");
        }
        return new SimpleStringProperty(str.toString());
    }

    public String getReceiversString() {
        return receiversStringProperty().get();
    }

    public LocalDateTime getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDateTime> dateProperty() {
        return date;
    }

    public String getFormattedDate() {
        return date.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm"));
    }

    public void setDate(LocalDateTime date) {
        this.date.set(date);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    private void init(){
        this.sender = new SimpleStringProperty();
        this.subject = new SimpleStringProperty();
        this.receivers = new SimpleListProperty<String>();
        this.date = new SimpleObjectProperty<>();
        this.message = new SimpleStringProperty();
    }

    private void writeObject(ObjectOutputStream s)throws IOException {
        //init();
        long millis = getDate().atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli();
        s.defaultWriteObject();
        s.writeUTF(getSender());
        s.writeUTF(getSubject());
        s.writeUTF(getReceiversString());
        s.writeLong(millis);
        s.writeUTF(getMessage());
    }

    private void readObject(ObjectInputStream s)throws IOException, ClassNotFoundException{
        init();
        setSender(s.readUTF());
        setSubject(s.readUTF());
        setReceivers(s.readUTF());
        //System.out.println("Local date Time"+LocalDateTime.ofInstant(Instant.ofEpochMilli(s.readLong()),TimeZone.getDefault().toZoneId()));
        setDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(s.readLong()),TimeZone.getDefault().toZoneId()));
        setMessage(s.readUTF());
    }

}
