package codec;

import modele.entite.Fichier;

public class MoteurImageSimulation  extends MoteurSimulation{

    public static String rechercher(int seuil, int colonne) throws IllegalArgumentException{
        if(seuil<0){
            throw new IllegalArgumentException("Le seuil est inférieur à 0");
        }
        if(colonne>2 || colonne<0){
            throw new IllegalArgumentException("La couleur ne correspond à rien de reconnu");
        }
        return "image.jpg 15 moi.bmp 45 vacances.bmp 1";
    }   

    public static String rechercher(Fichier f){
        //simulation pour les tests de recherches images
        return "fichier1.txt 89 fichier.xml 2.001 fichier.txt 94.457";
    }

}
