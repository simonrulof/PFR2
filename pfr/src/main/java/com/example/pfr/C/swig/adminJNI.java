/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.example.pfr.C.swig;

public class adminJNI {
  public final static native int lireDescripteur(String jarg1);
  public final static native int ajouterUnFichier(String jarg1, String jarg2, String jarg3, String jarg4, String jarg5);
  public final static native int supprFichier(String jarg1, String jarg2, String jarg3, String jarg4, String jarg5);
  public final static native int refaireUnDescripteur(String jarg1, String jarg2, String jarg3, String jarg4);
  public final static native int reindexerTousLesFichiers(String jarg1, String jarg2, String jarg3);
  public final static native void FICHIER_nomFichier_set(long jarg1, FICHIER jarg1_, String jarg2);
  public final static native String FICHIER_nomFichier_get(long jarg1, FICHIER jarg1_);
  public final static native void FICHIER_tauxDeSimilarite_set(long jarg1, FICHIER jarg1_, int jarg2);
  public final static native int FICHIER_tauxDeSimilarite_get(long jarg1, FICHIER jarg1_);
  public final static native long new_FICHIER();
  public final static native void delete_FICHIER(long jarg1);
  public final static native int MAX_get();
  public final static native void word_mot_set(long jarg1, word jarg1_, String jarg2);
  public final static native String word_mot_get(long jarg1, word jarg1_);
  public final static native void word_occ_set(long jarg1, word jarg1_, int jarg2);
  public final static native int word_occ_get(long jarg1, word jarg1_);
  public final static native long new_word();
  public final static native void delete_word(long jarg1);
  public final static native int indexerTxtSeul(String jarg1, String jarg2, String jarg3);
  public final static native int comparateurString(String jarg1, String jarg2);
  public final static native int comparerDescripteursTxt(long jarg1, int jarg2, String jarg3, long jarg4, FICHIER jarg4_);
  public final static native int comparerDescripteursTxtDoc(String jarg1, String jarg2, String jarg3, long jarg4, FICHIER jarg4_);
  public final static native long quantification(int jarg1, int jarg2, int jarg3);
  public final static native int indexationImage(String jarg1, String jarg2, String jarg3);
  public final static native int comparaisonImage(String jarg1, String jarg2, String jarg3, long jarg4, FICHIER jarg4_);
  public final static native void tabValeursIntervalles(long jarg1, int jarg2);
  public final static native int indexerAudio(String jarg1, String jarg2, String jarg3);
  public final static native int comparerAudio(String jarg1, String jarg2, String jarg3, long jarg4, FICHIER jarg4_, int jarg5, int jarg6);
  public final static native void triListeFichiers(long jarg1, FICHIER jarg1_);
}
