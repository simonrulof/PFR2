package codec;
import com.sun.jna.Library;
import com.sun.jna.Native;

public interface MoteurCImage extends Library {
    MoteurCImage INSTANCE = (MoteurCImage) Native.load("C/moteurCImage.so", MoteurCImage.class);

    String comparaisonImage(String dossierFichier, String nomFichierAComparer);

    void indexationImage(String dossierFichier, String  nomFichierAIndexer, String nomFichierStockage);


}
