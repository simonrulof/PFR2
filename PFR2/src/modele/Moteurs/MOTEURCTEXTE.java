package Moteurs;

import com.sun.jna.*;

import java.awt.*;

public interface MOTEURCTEXTE extends Library {
    MOTEURCTEXTE INSTANCE = (MOTEURCTEXTE) Native.load("C/moteurCTexte.so", MOTEURCTEXTE.class);

    String comparerDescripteursTxt(String[] motsCles, int i);

    String comparerDescripteursTxtDoc(String dossierFichierCherche, String NomFichierAChercher);

    int indexerTxtSeul(String dossierFichier, String nomFichierAIndexer, String nomFichierStockage);
}
