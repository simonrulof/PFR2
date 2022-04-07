package com.example.pfr.modele.entite;

import com.example.pfr.C.swig.admin;
import com.example.pfr.modele.donnee.TypeFichier;

public class Admin{

    static{
        System.loadLibrary("admin");
    }

    public void supprFichier(String nomFichier, String typeFichier){
        //return admin.supprFichier(CheminFichier, nomFichier, cheminDescripteur, fichierDescripteur, typeFichier)
    }

    public void ajouterUnFichier(String cheminFichier, String nomFichier, String typeFichier){
        //return admin.ajouterUnFichier(cheminFichier, nomFichier, cheminDossierFichiers, fichierDescripteur, typeFichier)
    }

    public void refaireUnDescripteur(String nomFichier, String typeFichier){
        //return admin.refaireUnDescripteur(dossierFichier, nomFichier, fichierDescripteur, typeFichier)
    }

    public void reindexerTousLesFichiers(TypeFichier type){
        //return admin.reindexerTousLesFichiers(nomFichierDescripteur, dossierFichier, type)
    }
}
  
  
