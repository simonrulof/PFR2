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
        //simulation pour les tests de recherches audio
        //return "fichier1.txt 26.2 fichier.wav 2.2 fichier.bin 100";

        //simulation pour les tests de recherches images
        //return "fichier1.bmp 89.7 fichier.jpg 24 fichier.bmp 94.457";

        //simulation pour les tests de recherches images
        return "fichier1.txt 89 fichier.xml 2.001 fichier.txt 94.457";
    }

    public static boolean indexer(Fichier f) {
        return false;
    }
}
