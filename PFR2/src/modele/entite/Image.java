package modele.entite;

import modele.donnee.TypeFichier;

public class Image extends Fichier{

    public Image(String nom, String chemin) {
        super(nom, chemin);
        this.setType(TypeFichier.IMAGE);
    }
    
}
