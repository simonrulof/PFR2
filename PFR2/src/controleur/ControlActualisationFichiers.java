package controleur;

import modele.thread.ThreadActualisationFichiers;

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
