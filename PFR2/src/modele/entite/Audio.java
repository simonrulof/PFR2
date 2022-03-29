package modele.entite;

import modele.donnee.TypeFichier;

public class Audio extends Fichier{

    public Audio(String nom, String chemin) throws IllegalArgumentException{
        super(nom, chemin);
        this.setType(TypeFichier.AUDIO);
        if(!(this.getExtension().contains(".bin")||this.getExtension().contains(".wav")||this.getExtension().contains(".txt"))){
            throw new IllegalArgumentException("extension non valide");
        }
    }

    public Audio(String titre) {
        super(titre);
        this.setType(TypeFichier.AUDIO);
        if(!(this.getExtension().contains(".bin")||this.getExtension().contains(".wav")||this.getExtension().contains(".txt"))){
            throw new IllegalArgumentException("extension non valide");
        }
    }
       
}
