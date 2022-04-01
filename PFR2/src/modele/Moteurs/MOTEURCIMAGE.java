import com.sun.jna.Library;
import com.sun.jna.Native;

public interface MOTEURCIMAGE extends Library {
    MOTEURCIMAGE INSTANCE = (MOTEURCIMAGE) Native.load("C/moteurCImage.so", MOTEURCIMAGE.class);

    String comparaisonImage(String dossierFichier, String nomFichierAComparer);

    void indexationImage(String dossierFichier, String  nomFichierAIndexer, String nomFichierStockage);


}
