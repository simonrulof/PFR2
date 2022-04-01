package modele.moteurs;
import java.io.File;

import codec.MOTEURCIMAGE;
import modele.entite.ResultatRequete;

public class MoteurImage extends Moteur{
    public MoteurImage(){}

    public void indexer(File nomFichier, String sortie) {
        MOTEURCIMAGE.INSTANCE.indexationImage(nomFichier.getParent() + "/", nomFichier.getName(), sortie);
    }

    public String comparerFichier(File nomFichier){
        String sortie;
        sortie = MOTEURCIMAGE.INSTANCE.comparaisonImage(nomFichier.getParent() + "/", nomFichier.getName());
        String[] mots;
        mots = sortie.split(" ");
        this.derniereRecherche = new ResultatRequete[mots.length/2];
        for(int i = 0; i < mots.length/2; i++){
            this.derniereRecherche[i] = new ResultatRequete(new File(mots[2*i]), Integer.parseInt(mots[2*i+1]));
        }
        return sortie;
    }
}
