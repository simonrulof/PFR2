package controleur;

import java.util.ResourceBundle.Control;

import modele.thread.ThreadActualisationFichiers;

public class ControlAcutalisationFichiers {
    private ControlAdministrateur ca;
    
    public ControlAcutalisationFichiers(ControlAdministrateur ca){
        this.ca = ca;
        ThreadActualisationFichiers taf = new ThreadActualisationFichiers(this.ca);
        taf.start();
    }
    
}
