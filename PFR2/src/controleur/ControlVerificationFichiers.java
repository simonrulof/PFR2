package controleur;

import java.io.File;

import modele.entite.Fichier;

public class ControlVerificationFichiers {
   
    public boolean fichierPresent(Fichier f) {
        boolean res = false;
        File dossier = new File(System.getProperty("user.dir"));
        for (int i = 0; i < dossier.listFiles().length; i++){
            if (dossier.listFiles()[i].isFile()&&dossier.listFiles()[i].getName().equals(f.getTitre())){
                res=true;
                break;
            } 
        }
        return res;
    }
    
}
