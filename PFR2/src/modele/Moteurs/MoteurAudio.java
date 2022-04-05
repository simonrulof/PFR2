package Moteurs;

import java.io.File;

public class MoteurAudio extends Moteur{
    public MoteurAudio(){}

    public void indexer(File nomFichier, String sortie) {
        MOTEURCAUDIO.INSTANCE.indexerAudio(nomFichier.getParent() + "/", nomFichier.getName(), sortie);
    }

    public String comparerFichier(File nomFichier){
        String sortie;
        sortie = MOTEURCAUDIO.INSTANCE.comparerAudio(nomFichier.getParent() + "/", nomFichier.getName());
        String[] mots;
        mots = sortie.split(" ");
        this.derniereRecherche = new Recherche[mots.length/2];
        for(int i = 0; i < mots.length/2; i++){
            this.derniereRecherche[i] = new Recherche(new File(mots[2*i]), Integer.parseInt(mots[2*i+1]));
        }
        return sortie;
    }
}
