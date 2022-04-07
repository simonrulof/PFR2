package modele.thread;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import codec.MoteurSimulation;
import controleur.ControlRechercheFichier;
import modele.entite.Fichier;

public class ThreadActualisationFichiers extends Thread{
    private boolean condition =  true;
    private Calendar temps;
    private String dossierCourant;
    private ControlRechercheFichier crf;
    private List<String> fichiers;
    /*Si JNA marche:
    private MoteurCAudio mca;
    private MoteurCImage mci;
    private MoteurCTexte mct;
    */

    public ThreadActualisationFichiers(){
        this.temps = Calendar.getInstance();
        //a modifier en fonction de ou on se situe        
        this.dossierCourant = System.getProperty("user.dir")+"/PFR2/C";
        this.crf = new ControlRechercheFichier();
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
                        MoteurSimulation.indexer(new Fichier(s));
                        /*Si JNA marche:
                        Fichier f = new Fichier(s);
                        if(f.getType()==TypeFichier.AUDIO){
                            this.mca.indexationAudio(this.dossierCourant,f.getName(),f.getName());
                        }
                        else if(f.getType()==TypeFichier.IMAGE){
                            this.mci.indexationImage(this.dossierCourant,f.getName(),f.getName());
                        } 
                        else{
                            this.mct.indexationTexte(this.dossierCourant,f.getName(),f.getName());
                        }*/
                        this.fichiers.add(s);
                    }
                }
            }
        }
    }
}

