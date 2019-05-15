package it.unito.brunasmail.model;

import java.io.*;


public class FileManager {
    private Mail mail;

    public FileManager(Mail mail){
        this.mail=mail;
    }

    public void toFile(){
        try {
            String splitUsr=mail.getReceiversString();
            String usr[]= splitUsr.split("@brunasmail.it");
            FileOutputStream f = new FileOutputStream("C:/Users/Beppe/IdeaProjects/prog3-server/src/it/unito/brunasmail/database/"+usr[0]+".txt");
            ObjectOutputStream o = new ObjectOutputStream(f);
            System.out.println(this.mail);
            o.writeObject(this.mail);
            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readFile(){

        try {
            String splitUsr=mail.getReceiversString();
            String usr[]= splitUsr.split("@brunasmail.it");
            FileInputStream fi = new FileInputStream(new File("C:/Users/Beppe/IdeaProjects/prog3-server/src/it/unito/brunasmail/database/"+usr[0]+".txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Mail readMail=(Mail)oi.readObject();
            System.out.println(readMail.toString());
            oi.close();
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static void main(String [] Args){
        FileManager f= new FileManager(new Mail("bruno@bruni.it", "Imporantissima", "beppe@brunasmail.it", 147925042110L, "Ciaoooooo"));
        f.toFile();
        f.readFile();
    }


}
