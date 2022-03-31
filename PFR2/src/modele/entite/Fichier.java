package modele.entite;

import java.io.File;

import modele.donnee.TypeFichier;

public class Fichier extends File{
    private String extension;
    private TypeFichier type;

    public Fichier(String nom){
        super(nom);       
        this.setExtension();
        this.setType();
    }

    public Fichier(String chemin, String nom){
        super(chemin,nom);
        this.setExtension();
        this.setType();
    }

    public Fichier(File parent, String nom){
        super(parent,nom);
        this.setExtension();
        this.setType();
    }

    private void setExtension(){
        int indexPoint = 0;
        for(int i=0; i<this.getName().length(); i++){
            if(this.getName().charAt(i)=='.'){
                indexPoint = i;
                break;
            }
        }
        this.extension = this.getName().substring(indexPoint);
    }

    public String getExtension(){
        return this.extension;
    }

    public TypeFichier getType(){
        return this.type;
    }

    public void setType() throws IllegalArgumentException{
        //techniquement on est pas sensé considérer les fichiers .txt comme audio mais vu que dans notre moteur on le fait
        if(this.getExtension().contains(".bin")||this.getExtension().contains(".wav")||this.getExtension().contains(".txt")){
            this.type = TypeFichier.AUDIO;
        }
        else if(this.getExtension().contains(".jpg")||this.getExtension().contains(".bmp")){
            this.type = TypeFichier.IMAGE;
        }
        else if(this.getExtension().contains(".xml")||this.getExtension().contains(".txt")){
            this.type = TypeFichier.TEXTE;
        }
        else{
            throw new IllegalArgumentException("Le type du fichier ne correspond à rien de reconnu par le moteur");
        }
    }
}
