package com.example.pfr.controleur;


import com.example.pfr.modele.thread.ThreadActualisationFichiers;

public class ControlActualisationFichiers {
    private ThreadActualisationFichiers taf;

    public ControlActualisationFichiers(){
        taf = new ThreadActualisationFichiers();
        taf.start();
    }

    public ThreadActualisationFichiers getThread(){
        return this.taf;
    }
    
}
