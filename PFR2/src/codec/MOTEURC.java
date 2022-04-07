package codec;
import com.sun.jna.*;

public interface MoteurC extends Library {
    MoteurC INSTANCE = (MoteurC) Native.load("C/malib.so", MoteurC.class);
    void test(String[] mots);
}
