package codec;
import com.sun.jna.*;

import java.awt.*;

public interface MoteurCTexte extends Library {
    MoteurCTexte INSTANCE = (MoteurCTexte) Native.load("C/moteurCTexte.so", MoteurCTexte.class);

    String comparerDescripteursTxt(String[] motsCles, int i);

    String comparerDescripteursTxtDoc(String dossierFichierCherche, String NomFichierAChercher);

    int indexerTxtSeul(String dossierFichier, String nomFichierAIndexer, String nomFichierStockage);
}
