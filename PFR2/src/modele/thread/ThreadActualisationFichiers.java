package modele.thread;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import codec.CodeCMoteur;
import controleur.ControlRechercheFile;
import modele.entite.Fichier;

public class ThreadActualisationFichiers extends Thread{
    private boolean condition =  true;
    private Calendar temps;
    private String dossierCourant;
    private ControlRechercheFile crf;
    private List<String> fichiers;

    public ThreadActualisationFichiers(){
        this.temps = Calendar.getInstance();
        //a modifier en fonction de ou on se situe        
        this.dossierCourant = System.getProperty("user.dir");
        this.crf = new ControlRechercheFile();
        this.crf.searchDirectory(new File(this.dossierCourant));
        this.fichiers.addAll(this.crf.getResult());
        this.crf.clearResult();
    }

    public void arret(){
        this.condition = false;
    }

    public void marche(){
        while(condition){
            try{
                //sommeil pendant 3 secondes
                Thread.sleep(3000);
            }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
            Calendar tempsCourant = Calendar.getInstance();
            temps.add(Calendar.MINUTE,2);
            if(tempsCourant.after(temps)){
                this.temps=tempsCourant;
                //verification dossier courant
                this.crf.searchDirectory(new File(this.dossierCourant));
                for(String s : this.crf.getResult()){
                    if(!this.fichiers.contains(s)){
                        CodeCMoteur.indexer(new Fichier(s));
                        this.fichiers.add(s);
                    }
                }
            }
        }
    }
}

