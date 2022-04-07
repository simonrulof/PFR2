package modele.moteur;

import java.io.File;

import modele.entite.ResultatRequete;

public abstract class Moteur {
    protected ResultatRequete derniereRecherche[];

    public Moteur(){}

    public abstract void indexer(File nomFichier, String sortie);

    public abstract String comparerFichier(File nomFichier);

    public ResultatRequete[] getDerniereRecherche() {
        return this.derniereRecherche;
    }
}
