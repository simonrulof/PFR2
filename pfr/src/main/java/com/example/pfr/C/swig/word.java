/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.example.pfr.C.swig;

import com.example.pfr.C.swig.adminJNI;

public class word {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected word(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(word obj) {
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
        adminJNI.delete_word(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setMot(String value) {
    adminJNI.word_mot_set(swigCPtr, this, value);
  }

  public String getMot() {
    return adminJNI.word_mot_get(swigCPtr, this);
  }

  public void setOcc(int value) {
    adminJNI.word_occ_set(swigCPtr, this, value);
  }

  public int getOcc() {
    return adminJNI.word_occ_get(swigCPtr, this);
  }

  public word() {
    this(adminJNI.new_word(), true);
  }

}
