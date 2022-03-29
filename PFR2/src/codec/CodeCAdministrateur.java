package codec;

import java.io.File;

import modele.entite.Fichier;

public class CodeCAdministrateur{

    public static boolean supprimer(Fichier f) {
        //appel de la methode pour supprimer un descripteur
        File dossier = new File(System.getProperty("user.dir"));
        for (int i = 0; i < dossier.listFiles().length; i++){
            if (dossier.listFiles()[i].isFile()&&dossier.listFiles()[i].getName().equals(f.getTitre())){
                dossier.listFiles()[i].delete();
                break;
            } 
        }
        return false;
    }

    public static boolean ajouter(Fichier f) {
        File dossier = new File(System.getProperty("user.dir"));
        File fichierAjouter = new File(dossier, f.getTitre());
        CodeCMoteur.indexer(f);
        return false;
    }
    
}
