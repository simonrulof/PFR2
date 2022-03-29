package codec;

import modele.entite.Fichier;

public abstract class CodeCMoteur {
    
    public static boolean indexerTout(){
        return false;
    }

    public static String descripteur(Fichier f) {
        return null;
    }
    
    public static String rechercher(String titre) {
        return null;
    }

    public static boolean indexer(Fichier f) {
        return false;
    }
}
