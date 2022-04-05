package controleur;

import modele.thread.ThreadActualisationFichiers;

public class ControlAcutalisationFichiers {
    private ThreadActualisationFichiers taf;

    public ControlAcutalisationFichiers(){
        taf = new ThreadActualisationFichiers();
        taf.start();
    }

    public ThreadActualisationFichiers getThread(){
        return this.taf;
    }


    
}
