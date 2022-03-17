package modele.entite;

import modele.donnee.TypeFichier;

public class Texte extends Fichier{

    public Texte(String nom, String chemin) {
        super(nom, chemin);
        this.setType(TypeFichier.TEXTE);
    }
    
}
