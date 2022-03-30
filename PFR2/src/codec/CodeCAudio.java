package codec;

import modele.entite.Audio;

public class CodeCAudio  extends CodeCMoteur{

    public static String rechercheOccurrence(Audio a) {
        return "rechercheOccurence("+a.getTitre()+")";
    }

}
