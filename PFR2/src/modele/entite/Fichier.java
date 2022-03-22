package modele.entite;

import modele.donnee.TypeFichier;

public class Fichier {
    private String titre;
    private String chemin;
    private String extension;
    private int taille;
    private TypeFichier type;

    public Fichier(String nom, String chemin){
        this.titre = nom;
        this.chemin = chemin;
    }

    private void setExtension(){
        int indexPoint = 0;
        for(int i=0; i<this.titre.length(); i++){
            if(this.titre.charAt(i)=='.'){
                indexPoint = i;
                break;
            }
        }
        this.extension = this.titre.substring(indexPoint+1);
    }

    public String getExtension(){
        return this.extension;
    }

    public String getTitre(){
        return this.titre;
    }

    public String getChemin(){
        return this.chemin;
    }

    public TypeFichier getType(){
        return this.type;
    }

    public void setType(TypeFichier t){
        this.type = t;
    }
}
