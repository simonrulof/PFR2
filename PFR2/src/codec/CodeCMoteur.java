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
        return "fichier1.txt 26.2 fichier.wav 2.2 fichier.bin 100";
    }

    public static boolean indexer(Fichier f) {
        return false;
    }
}
