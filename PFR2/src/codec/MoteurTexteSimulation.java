package codec;

import modele.entite.Fichier;

public class MoteurTexteSimulation  extends MoteurSimulation{
    public static String rechercheMot(String motCle) {
        return "texte1.xml 12 texte2.xml 11 texte4.xml 3";
    }

    public static String rechercher(Fichier f) {
        //simulation pour les tests de recherches images
        return "texte1.xml 89 texte2.xml 2.001 texte4.xml 94.457";
    }

}
