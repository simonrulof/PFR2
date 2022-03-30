package controleur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import modele.bd.BDHistorique;
import modele.entite.Recherche;

public class ControlUtilisateur {    
    private ControlHistorique ch;
    private ControlRechercheComplexeAudio crca;
    private ControlRechercheComplexeImage crci;
    private ControlRechercheComplexeTexte crct;

    public ControlUtilisateur(ControlHistorique ch,ControlRechercheComplexeAudio crca, ControlRechercheComplexeImage crci, ControlRechercheComplexeTexte crct){
        this.crca=crca;
        this.crci=crci;
        this.crct=crct;
        this.ch=ch;
    }
    
    public boolean effectuerRecherche(Recherche r){
        
        this.ch.ajoutRecherche(r);
        return false;
    }

    public Map<String, Recherche> consulterHistorique(){
        return this.ch.consulterHistorique();
    }

    public void supprimerRecherche(Recherche r){
        this.ch.supprimerRecherche(r);
    }

    public void supprimerHistorique(){
        this.ch.viderHistorique();        
    }
}
