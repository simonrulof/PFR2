package codec;
import com.sun.jna.Library;
import com.sun.jna.Native;

public interface MoteurCAudio extends Library {
    MoteurCAudio INSTANCE = (MoteurCAudio) Native.load("C/moteurCAudio.so", MoteurCAudio.class);

    int indexerAudio(String cheminFichier, String nomFichier, String descripteur);

    String comparerAudio(String cheminFichier, String nomFichier);


}
