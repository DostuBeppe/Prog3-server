package it.unito.brunasmail.server.model;

import javafx.beans.property.*;

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
    private final IntegerProperty id;
    private final StringProperty sender;
    private final StringProperty subject;
    private List<String> receivers;
    private final ObjectProperty<LocalDateTime> date;
    private final StringProperty message;
    private Boolean viewed;

    public Mail(Integer id, String sender, String subject, String receivers, long timestamp, String message, Boolean viewed) {
        this.id = new SimpleIntegerProperty(id);
        this.sender = new SimpleStringProperty(sender);
        this.subject = new SimpleStringProperty(subject);
        if(receivers!=null) setReceivers(receivers);
        this.date = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId()));
        this.message = new SimpleStringProperty(message);
        this.viewed = viewed;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public void removeFromReceivers(String mail){
        ArrayList<String> tmp = new ArrayList<>(receivers);
        tmp.remove(mail);
        receivers = tmp;
    }
    public void setReceivers(String r) {
        ArrayList<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(r);
        while (m.find()) {
            list.add(m.group());
        }
        //String[] array = r.split("\\\\s*;\\\\s*",-1);
        receivers = list;
    }
    public StringProperty receiversStringProperty() {
        if(receivers == null){
            return new SimpleStringProperty("");
        }
        StringBuilder str = new StringBuilder();
        for(String s: receivers){
            System.out.println("Dest: " + s);
            str.append(s).append("; ");
        }
        return new SimpleStringProperty(str.toString());
    }

    public String getReceiversString(){
        return receiversStringProperty().get();
    }


    public LocalDateTime getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDateTime> dateProperty() {
        return date;
    }

    public String getFormattedDate(){
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

    public Boolean getViewed() {
        return viewed;
    }


}
