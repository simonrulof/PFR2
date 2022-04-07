package com.example.pfr.modele.thread;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import com.example.pfr.C.swig.texte;
import com.example.pfr.C.swig.image;
import com.example.pfr.C.swig.audio;
import com.example.pfr.controleur.ControlRechercheFichier;
import com.example.pfr.modele.donnee.TypeFichier;
import com.example.pfr.modele.entite.Fichier;

public class ThreadActualisationFichiers extends Thread{
    private boolean condition =  true;
    private Calendar temps;
    private String dossierCourant;
    private ControlRechercheFichier crf;
    private List<String> fichiers;

    static{
        System.loadLibrary("audio");
        System.loadLibrary("texte");
        System.loadLibrary("image");
    }

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
                        Fichier f = new Fichier(s);
                        if(f.getType()==TypeFichier.AUDIO){
                            audio.indexerAudio(this.dossierCourant,f.getName(),f.getName());
                        }
                        else if(f.getType()==TypeFichier.IMAGE){
                            image.indexationImage(this.dossierCourant,f.getName(),f.getName());
                        } 
                        else{
                            texte.indexerTxtSeul(this.dossierCourant,f.getName(),f.getName());
                        }
                        this.fichiers.add(s);
                    }
                }
            }
        }
    }
}

