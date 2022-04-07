package com.example.pfr.controleur;

import java.io.File;

import com.example.pfr.modele.entite.Fichier;


public class ControlVerificationFichiers {
   
    public boolean fichierPresent(Fichier f, String dossier) {
        boolean res = false;
        File dossierCourant = new File(dossier);
        for (int i = 0; i < dossierCourant.listFiles().length; i++){
            if (dossierCourant.listFiles()[i].isFile()&&dossierCourant.listFiles()[i].getName().equals(f.getName())){
                res=true;
                break;
            } 
        }
        return res;
    }
    
}
