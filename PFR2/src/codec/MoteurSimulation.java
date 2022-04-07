package codec;

import modele.entite.Fichier;

public abstract class MoteurSimulation {
    
    public static boolean indexerTout(){
        return false;
    }

    public static String descripteur(Fichier f) {
        return "descripteur("+f.getAbsolutePath()+")";
    }
    
    public static String indexer(Fichier f) {
        return "indexation du fichier : "+f.getAbsolutePath();
    }
}
