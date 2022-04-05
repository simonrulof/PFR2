package modele.entite;
import java.io.File;

public class ResultatRequete {
    private File nomDoc;
    private int similarite;

    public ResultatRequete(File nomDoc, int similarite){
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
