package modele.entite;

import modele.donnee.TypeFichier;

public class Audio extends Fichier{

    public Audio(String nom, String chemin) {
        super(nom, chemin);
        this.setType(TypeFichier.AUDIO);
    }
       
}
