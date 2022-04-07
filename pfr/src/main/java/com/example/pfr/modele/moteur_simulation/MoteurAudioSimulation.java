package com.example.pfr.modele.moteur_simulation;


import com.example.pfr.modele.donnee.TypeFichier;
import com.example.pfr.modele.entite.Fichier;

public class MoteurAudioSimulation  extends MoteurSimulation{

    public static String rechercheOccurrence(Fichier a) throws IllegalArgumentException{
        if(a.getType()== TypeFichier.AUDIO){
            return "corpus_fi.bin 2 1:23 1:34 corpus_fi.wav 2 1:23 1:34 jingle_fi.bin 1 0:00 jingle_fi.wav 1 0:00";
        }
        else{
            throw new IllegalArgumentException("Le fichier n'est pas du bon type");
        }
    }

    public static String rechercher(Fichier f){
        //simulation pour les tests de recherches audio
        return "corpus_fi.bin 26.2 corpus_fi.wav 26.2 jingle_fi.bin 100 jingle_fi.wav 100";
    }

}
