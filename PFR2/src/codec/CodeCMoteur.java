package codec;

import modele.entite.Fichier;

public abstract class CodeCMoteur {
    
    public static boolean indexerTout(){
        return false;
    }

    public static String descripteur(Fichier f) {
        return "descripteur("+f.getTitre()+")";
    }
    
    public static String rechercher(Fichier f) {
        return "rechercher("+f.getTitre()+")";
    }

    public static boolean indexer(Fichier f) {
        return false;
    }
}
