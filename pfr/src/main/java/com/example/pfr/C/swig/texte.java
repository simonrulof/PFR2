/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.example.pfr.C.swig;

public class texte implements texteConstants {
  public static int indexerTxtSeul(String dossierFichier, String nomFichierAIndexer, String nomFichierStockage) {
    return texteJNI.indexerTxtSeul(dossierFichier, nomFichierAIndexer, nomFichierStockage);
  }

  public static int comparateurString(String string1, String string2) {
    return texteJNI.comparateurString(string1, string2);
  }

  public static int comparerDescripteursTxt(SWIGTYPE_p_p_char motsCles, int i, String fichierDescripteur, FICHIER listeFichiers) {
    return texteJNI.comparerDescripteursTxt(SWIGTYPE_p_p_char.getCPtr(motsCles), i, fichierDescripteur, FICHIER.getCPtr(listeFichiers), listeFichiers);
  }

  public static int comparerDescripteursTxtDoc(String dossierFichierCherche, String NomFichierAChercher, String fichierDescripteur, FICHIER listeFichiers) {
    return texteJNI.comparerDescripteursTxtDoc(dossierFichierCherche, NomFichierAChercher, fichierDescripteur, FICHIER.getCPtr(listeFichiers), listeFichiers);
  }

}
