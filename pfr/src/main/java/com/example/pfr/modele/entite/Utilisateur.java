package com.example.pfr.modele.entite;

import com.example.pfr.controleur.ControlHistorique;
import com.example.pfr.controleur.ControlRechercheComplexeAudio;
import com.example.pfr.controleur.ControlRechercheComplexeImage;
import com.example.pfr.controleur.ControlRechercheComplexeTexte;
import com.example.pfr.controleur.ControlVerificationFichiers;

public class Utilisateur {
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

        Recherche recherche;

        if (texte){
            ControlRechercheComplexeTexte crct = new ControlRechercheComplexeTexte(this.cvf,multimoteur);
            recherche = crct.rechercher(requete);
            ch.ajoutRecherche(recherche);
            if(recherche.getResultatsRequeteArguments().isEmpty()){
                //le resultat de la requete se trouve dans recherche.getResultats()
                
            }
            else{
                //le resultat de la requete se trouve dans recherche.getResultatsRequeteArguments()
                
            }
            return fichiersTexte;
        }
        else if(image){
            ControlRechercheComplexeImage crci = new ControlRechercheComplexeImage(this.cvf, multimoteur);
            recherche = crci.rechercher(requete);
            ch.ajoutRecherche(recherche);
            if(recherche.getResultatsRequeteArguments().isEmpty()){
                //le resultat de la requete se trouve dans recherche.getResultats()
                
            }
            else{
                //le resultat de la requete se trouve dans recherche.getResultatsRequeteArguments()
                
            }
            return fichiersImage;
        }
        else if(audio){
            ControlRechercheComplexeAudio crca = new ControlRechercheComplexeAudio(this.cvf, multimoteur);
            recherche = crca.rechercher(requete);
            ch.ajoutRecherche(recherche);
            if(recherche.getResultatsRequeteArguments().isEmpty()){
                //le resultat de la requete se trouve dans recherche.getResultats()
                
            }
            else{
                //le resultat de la requete se trouve dans recherche.getResultatsRequeteArguments()
                
            }
            return fichiersAudio;
        }
        return null;
    }
}
