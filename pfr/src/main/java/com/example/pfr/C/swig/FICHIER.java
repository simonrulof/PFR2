/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.example.pfr.C.swig;

public class FICHIER {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected FICHIER(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FICHIER obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        adminJNI.delete_FICHIER(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setNomFichier(String value) {
    adminJNI.FICHIER_nomFichier_set(swigCPtr, this, value);
  }

  public String getNomFichier() {
    return adminJNI.FICHIER_nomFichier_get(swigCPtr, this);
  }

  public void setTauxDeSimilarite(int value) {
    adminJNI.FICHIER_tauxDeSimilarite_set(swigCPtr, this, value);
  }

  public int getTauxDeSimilarite() {
    return adminJNI.FICHIER_tauxDeSimilarite_get(swigCPtr, this);
  }

  public FICHIER() {
    this(adminJNI.new_FICHIER(), true);
  }

}
