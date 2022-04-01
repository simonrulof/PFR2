import java.io.File;

public abstract class Moteur {
    protected Recherche derniereRecherche[];

    public Moteur(){}

    public abstract void indexer(File nomFichier, String sortie);

    public abstract String comparerFichier(File nomFichier);

    public Recherche[] getDerniereRecherche() {
        return this.derniereRecherche;
    }
}
