import java.io.File;

public class Recherche {
    private File nomDoc;
    private int similarite;

    public Recherche(File nomDoc, int similarite){
        this.nomDoc = nomDoc;
        this.similarite = similarite;
    }

    public File getNomDoc() {
        return nomDoc;
    }

    public int getSimilarite() {
        return similarite;
    }

    public String toString(){
        return this.nomDoc + " " + this.similarite;
    }
}
