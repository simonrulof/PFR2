/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.example.pfr.C.swig;

public class audioJNI {
  public final static native void tabValeursIntervalles(long jarg1, int jarg2);
  public final static native int indexerAudio(String jarg1, String jarg2, String jarg3);
  public final static native int comparerAudio(String jarg1, String jarg2, String jarg3, long jarg4, FICHIER jarg4_, int jarg5, int jarg6);
  public final static native void triListeFichiers(long jarg1, FICHIER jarg1_);
  public final static native void FICHIER_nomFichier_set(long jarg1, FICHIER jarg1_, String jarg2);
  public final static native String FICHIER_nomFichier_get(long jarg1, FICHIER jarg1_);
  public final static native void FICHIER_tauxDeSimilarite_set(long jarg1, FICHIER jarg1_, int jarg2);
  public final static native int FICHIER_tauxDeSimilarite_get(long jarg1, FICHIER jarg1_);
  public final static native long new_FICHIER();
  public final static native void delete_FICHIER(long jarg1);
}