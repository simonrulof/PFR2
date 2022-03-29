package controleur;

import codec.CodeCImage;
import modele.donnee.Couleur;
import modele.entite.Image;

public class ControlRechercheComplexeImage {
    private ControlVerificationFichiers cvf;

    public ControlRechercheComplexeImage(ControlVerificationFichiers cvf){
        this.cvf = cvf;
    }

    public String recherche(Image i) throws IllegalArgumentException{   
        String res=null; 
        if(this.cvf.fichierPresent(i)){
            res=CodeCImage.rechercher(i.getTitre());
        } 
        else{
            throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
        }
        return res;
    }

    public String rechercheCouleur(int seuil, Couleur c){
        return CodeCImage.rechercher(seuil,c);
    }


}
