/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.example.pfr.C.swig;

public class audio {
  public static void tabValeursIntervalles(SWIGTYPE_p_float tab, int nbIntervalle) {
    audioJNI.tabValeursIntervalles(SWIGTYPE_p_float.getCPtr(tab), nbIntervalle);
  }

  public static int indexerAudio(String cheminFichier, String nomFichier, String descripteur) {
    return audioJNI.indexerAudio(cheminFichier, nomFichier, descripteur);
  }

  public static int comparerAudio(String cheminFichier, String nomFichier, String descripteurs, FICHIER listeFichiers, int nbIntervalle, int echantillonage) {
    return audioJNI.comparerAudio(cheminFichier, nomFichier, descripteurs, FICHIER.getCPtr(listeFichiers), listeFichiers, nbIntervalle, echantillonage);
  }

  public static void triListeFichiers(FICHIER f) {
    audioJNI.triListeFichiers(FICHIER.getCPtr(f), f);
  }

}