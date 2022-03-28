package modele.entite;

import modele.donnee.TypeFichier;

public class Texte extends Fichier{

    public Texte(String nom, String chemin) {
        super(nom, chemin);
        this.setType(TypeFichier.TEXTE);
        if(!(this.getExtension().contains(".xml")||this.getExtension().contains(".txt"))){
            throw new IllegalArgumentException("extension non valide");
        }
    }    
}
