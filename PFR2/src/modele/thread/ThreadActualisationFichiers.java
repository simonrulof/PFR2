package modele.thread;

import java.io.File;
import java.util.Calendar;

import controleur.ControlAdministrateur;

public class ThreadActualisationFichiers extends Thread{
    private boolean condition =  true;
    private Calendar temps;
    private String dossierCourant;
    private ControlAdministrateur ca;

    public ThreadActualisationFichiers(ControlAdministrateur ca){
        this.temps = Calendar.getInstance();
        this.ca = ca;        
        this.dossierCourant = System.getProperty("user.dir");
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
                File dossier = new File(this.dossierCourant);
                this.parcoursFichiers(dossier);                
            }
        }
    }

    private void parcoursFichiers(File dossier){
        String f = null;
        for (int i = 0; i < dossier.listFiles().length; i++){
            if (dossier.listFiles()[i].isFile()){
                //appel de la méthode en c via controleur pour ajouter les descripteurs à la base de données
                f = dossier.listFiles()[i].getName();
                this.ca.addDescripteur(new File(f));
            } 
            else if (dossier.listFiles()[i].isDirectory()) {
                f = dossier.listFiles()[i].getName();
                parcoursFichiers(new File(f));
            }
            else{
                //fichiers non reconnu
            }
        }
    }
}

