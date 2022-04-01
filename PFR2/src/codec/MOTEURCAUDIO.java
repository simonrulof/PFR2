package codec;
import com.sun.jna.Library;
import com.sun.jna.Native;

public interface MOTEURCAUDIO extends Library {
    MOTEURCAUDIO INSTANCE = (MOTEURCAUDIO) Native.load("C/moteurCAudio.so", MOTEURCAUDIO.class);

    int indexerAudio(String cheminFichier, String nomFichier, String descripteur);

    String comparerAudio(String cheminFichier, String nomFichier);


}
