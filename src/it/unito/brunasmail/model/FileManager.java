package it.unito.brunasmail.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


public class FileManager {

    public synchronized static void save(Mail mail){
        try {
            Date date = new Date();
            long millis = date.getTime();
            String sender = mail.getSender();
            List<String> receivers = mail.getReceivers();
            FileOutputStream f = new FileOutputStream("./files/"+sender+"/"+"out/"+millis+".txt");
            ObjectOutputStream o = new ObjectOutputStream(f);
            Mail outputStream=new Mail(mail.getSender(),mail.getSubject(),mail.getReceiversString(), millis,mail.getMessage());
            outputStream.setSent(true);
            o.writeObject(outputStream);
            o.close();
            f.close();
            for (String r : receivers){
                System.out.println(r);
                f = new FileOutputStream("./files/"+r+"/"+"in/"+millis+".txt");
                o = new ObjectOutputStream(f);
                o.writeObject(new Mail(mail.getSender(),mail.getSubject(),mail.getReceiversString(), millis,mail.getMessage()));
                o.close();
                f.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized static List<Mail> loadInbox(String user){
        List<Mail> inbox = new ArrayList<>();
        try {
            File dir = new File("./files/"+user+"/"+"in/");
            ObjectInputStream oi = null;
            FileInputStream fis = null;
            for (File f : Objects.requireNonNull(dir.listFiles())){
                fis = new FileInputStream(f);
                oi = new ObjectInputStream(fis);
                inbox.add((Mail)oi.readObject());
                oi.close();
                fis.close();
            }
            if (fis != null){
                oi.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inbox;
    }

    public synchronized static List<Mail> getUpdatedList(String user, String max){
        List<Mail> updatedMail=new ArrayList<>();
        max=max+".txt";
        File dir = new File("./files/"+user+"/"+"in/");
        ObjectInputStream oi = null;
        FileInputStream fis = null;
        for (File f : Objects.requireNonNull(dir.listFiles())){
            if(f.getName().compareTo(max)>0){
                try {
                    fis = new FileInputStream(f);
                    oi = new ObjectInputStream(fis);
                    updatedMail.add((Mail)oi.readObject());
                    oi.close();
                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return updatedMail;
    }


    public synchronized static List<Mail> loadOutbox(String user){
        List<Mail> outbox = new ArrayList<>();
        try {
            File dir = new File("./files/"+user+"/"+"out/");
            ObjectInputStream oi = null;
            FileInputStream fis = null;
            for (File f : Objects.requireNonNull(dir.listFiles())){
                fis = new FileInputStream(f);
                oi = new ObjectInputStream(fis);
                outbox.add((Mail)oi.readObject());
                oi.close();
                fis.close();
            }
            if (fis != null){
                oi.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outbox;
    }

    public synchronized static void delete(Mail  mail, String user){

        try {
            if(mail.isSent()) {
                Files.delete(Paths.get("./files/" + user + "/out/" + mail.getMillis() + ".txt"));
            } else  {
                Files.delete(Paths.get("./files/" + user + "/in/" + mail.getMillis() + ".txt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
