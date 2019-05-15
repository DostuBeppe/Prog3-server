package it.unito.brunasmail.model;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;


public class FileManager {

    public static void toFile(Mail mail){
        try {
            List<String> splitUsr=mail.getReceivers();
            FileOutputStream f = new FileOutputStream("./files/"+splitUsr.get(0)+".txt");
            ObjectOutputStream o = new ObjectOutputStream(f);
            long millis = Calendar.getInstance().get(Calendar.MILLISECOND);
            o.writeObject(new Mail(mail.getSender(),mail.getSubject(),mail.getReceiversString(), millis,mail.getMessage()));
            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readFile(String user){

        try {
            FileInputStream fi = new FileInputStream(new File("./files/"+user+".txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Mail readMail=(Mail)oi.readObject();
            System.out.println("MITTENTE - FILE LETTO");
            System.out.println(readMail.getSender());
            oi.close();
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
