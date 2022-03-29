package modele.entite;

import modele.donnee.TypeFichier;

public class Image extends Fichier{

    public Image(String nom, String chemin) throws IllegalArgumentException{
        super(nom, chemin);
        this.setType(TypeFichier.IMAGE);
        if(!(this.getExtension().contains(".jpg")||this.getExtension().contains(".bmp"))){
            throw new IllegalArgumentException("extension non valide");
        }
    }

    public Image(String nom) {
        super(nom);
        this.setType(TypeFichier.IMAGE);
        if(!(this.getExtension().contains(".jpg")||this.getExtension().contains(".bmp"))){
            throw new IllegalArgumentException("extension non valide");
        }
    }
    
}
