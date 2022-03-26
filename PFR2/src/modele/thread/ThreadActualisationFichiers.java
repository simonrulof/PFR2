package modele.thread;

import java.io.File;
import java.util.Calendar;

public class ThreadActualisationFichiers extends Thread{
    private boolean condition =  true;
    private Calendar temps;
    private String dossierCourant;

    public ThreadActualisationFichiers(){
        this.temps = Calendar.getInstance();
    }

    public void arret(){
        this.condition = false;
    }

    public void marche(){
        while(condition){
            try{
                //sommeil pendant 30 secondes
                Thread.sleep(30000);
            }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
            Calendar tempsCourant = Calendar.getInstance();
            temps.add(Calendar.MINUTE,2);
            if(tempsCourant.after(temps)){
                this.temps=tempsCourant;
                //verification dossier courant
                dossierCourant = System.getProperty("user.dir");
                File[] listOfFiles = dossierCourant.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
                }
                //appel de la méthode en c via controleur pour ajouter les descripteurs à la base de données

            }
        }
    }
}
