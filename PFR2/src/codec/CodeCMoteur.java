package codec;

import modele.entite.Fichier;

public abstract class CodeCMoteur {
    
    public static boolean indexerTout(){
        return false;
    }

    public static String descripteur(Fichier f) {
        return "descripteur("+f.getAbsolutePath()+")";
    }
    
    public static String rechercher(Fichier f) {
        return "rechercher("+f.getAbsolutePath()+")";
    }

    public static boolean indexer(Fichier f) {
        return false;
    }
}
