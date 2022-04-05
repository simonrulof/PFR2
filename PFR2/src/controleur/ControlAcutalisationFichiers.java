package controleur;

import modele.thread.ThreadActualisationFichiers;

public class ControlAcutalisationFichiers {
    
    public ControlAcutalisationFichiers(){
        ThreadActualisationFichiers taf = new ThreadActualisationFichiers();
        taf.start();
    }
    
}
