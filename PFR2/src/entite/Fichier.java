package entite;

public class Fichier {
    private String titre;
    private String chemin;
    private String extension;
    private int taille;

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

}
