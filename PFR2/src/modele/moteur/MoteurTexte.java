package modele.moteur;

import java.io.File;
import codec.MoteurCTexte;
import modele.entite.ResultatRequete;

public class MoteurTexte extends Moteur{
    public MoteurTexte(){}

    public void indexer(File nomFichier, String sortie) {
        MoteurCTexte.INSTANCE.indexerTxtSeul(nomFichier.getParent() + "/", nomFichier.getName(), sortie);
    }

    public String comparerFichier(String[] motsCles){
        String sortie;
        sortie = MoteurCTexte.INSTANCE.comparerDescripteursTxt(motsCles, motsCles.length);
        String[] mots;
        mots = sortie.split(" ");
        this.derniereRecherche = new ResultatRequete[mots.length/2];
        for(int i = 0; i < mots.length/2; i++){
            this.derniereRecherche[i] = new ResultatRequete(new File(mots[2*i]), Integer.parseInt(mots[2*i+1]));
        }
        return sortie;
    }

    public String comparerFichier(File nomFichier){
        String sortie;
        sortie = MoteurCTexte.INSTANCE.comparerDescripteursTxtDoc(nomFichier.getParent() + "/", nomFichier.getName());
        String[] mots;
        mots = sortie.split(" ");
        this.derniereRecherche = new ResultatRequete[mots.length/2];
        for(int i = 0; i < mots.length/2; i++){
            this.derniereRecherche[i] = new ResultatRequete(new File(mots[2*i]), Integer.parseInt(mots[2*i+1]));
        }
        return sortie;
    }
}
