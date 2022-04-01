package codec;
import com.sun.jna.*;

public interface MOTEURC extends Library {
    MOTEURC INSTANCE = (MOTEURC) Native.load("C/malib.so", MOTEURC.class);
    void test(String[] mots);
}
