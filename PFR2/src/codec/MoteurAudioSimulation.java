package codec;

import modele.donnee.TypeFichier;
import modele.entite.Fichier;

public class MoteurAudioSimulation  extends MoteurSimulation{

    public static String rechercheOccurrence(Fichier a) throws IllegalArgumentException{
        if(a.getType()==TypeFichier.AUDIO){
            return "emission.bin 2 1:23 1:34 show.wav 1 1:23 luxe.bin 3 2:34 4:45 5:43";
        }
        else{
            throw new IllegalArgumentException("Le fichier n'est pas du bon type");
        }
    }

}
