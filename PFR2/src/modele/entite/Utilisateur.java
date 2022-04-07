package modele.entite;

import java.io.File;

import controleur.ControlHistorique;
import controleur.ControlRechercheComplexeAudio;
import controleur.ControlRechercheComplexeImage;
import controleur.ControlRechercheComplexeTexte;
import controleur.ControlVerificationFichiers;
import modele.moteur.*;

public class Utilisateur {
    private Moteur moteurTexte = new MoteurTexte();
    private MoteurImage moteurImage = new MoteurImage();
    private MoteurAudio moteurAudio = new MoteurAudio();
    private ControlVerificationFichiers cvf = new ControlVerificationFichiers();

    private Utilisateur(){}

    private static class UtilisateurHolder{
        private static final Utilisateur instance = new Utilisateur();
    }

    public static Utilisateur getInstance(){
        return UtilisateurHolder.instance;
    }
   
    public ResultatRequete[] comparer(String requete, boolean multimoteur, boolean texte, boolean image, boolean audio, ControlHistorique ch){
        ResultatRequete[] fichiersTexte = new ResultatRequete[100];
        ResultatRequete[] fichiersImage = new ResultatRequete[100];
        ResultatRequete[] fichiersAudio = new ResultatRequete[100];

        Recherche recherche = null;
        if (texte){
            ControlRechercheComplexeTexte crct = new ControlRechercheComplexeTexte(this.cvf,multimoteur);
            recherche = crct.rechercher(requete);
        }
        else if(image){
            ControlRechercheComplexeImage crci = new ControlRechercheComplexeImage(this.cvf, multimoteur);
            recherche = crci.rechercher(requete);            
        }
        else if(audio){
            ControlRechercheComplexeAudio crca = new ControlRechercheComplexeAudio(this.cvf, multimoteur);
            recherche = crca.rechercher(requete);
        }
        ch.ajoutRecherche(recherche);
        return null;
    }
}
