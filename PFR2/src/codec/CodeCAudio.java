package codec;

import modele.donnee.TypeFichier;
import modele.entite.Fichier;

public class CodeCAudio  extends CodeCMoteur{

    public static String rechercheOccurrence(Fichier a) throws IllegalArgumentException{
        if(a.getType()==TypeFichier.AUDIO){
            return "rechercheOccurence("+a.getName()+")";
        }
        else{
            throw new IllegalArgumentException("Le fichier n'est pas du bon type");
        }
    }

}
