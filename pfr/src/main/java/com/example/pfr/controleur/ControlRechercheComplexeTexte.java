package com.example.pfr.controleur;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.pfr.modele.donnee.TypeRecherche;
import com.example.pfr.modele.entite.Fichier;
import com.example.pfr.modele.entite.Recherche;
import com.example.pfr.modele.moteur_simulation.MoteurTexteSimulation;

public class ControlRechercheComplexeTexte {
    private ControlVerificationFichiers cvf;
    private boolean multimoteur;

    public ControlRechercheComplexeTexte(ControlVerificationFichiers cvf, boolean multimoteur){
        this.cvf = cvf;
        this.multimoteur=multimoteur;
    }

    static{
        //Je ne l'ajoute que pour faire jolie
        //les fonctions en c ne sont pas utilisable
        System.loadLibrary("texte");
    }

    public Recherche rechercher(String requete){
        Recherche r;
        if(requete.contains("/")||requete.contains(".")){
            r = rechercheExemple(requete);
        }
        else{
            r = rechercheMotCle(requete);
        }        
        r.setRequete(requete);
        return r;
    }

    private Recherche rechercheMotCle(String requete) throws IllegalArgumentException{   
        HashMap<String,String> hm = traitementRequete(requete);
        Recherche r = new Recherche(new Fichier(".txt"), requete, TypeRecherche.MOTCLES);
        for(String s : hm.keySet()){
            if(!s.matches("[a-zA-Z]+")){
                //contient caractères spéciaux
                throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
            } 
        }
        HashMap<Fichier,String> resultat = new HashMap<>();
        if(this.multimoteur){
            HashMap<String,String> r1 = rechercheMotCleComparaison(hm);
            HashMap<String,String> r2 = rechercheMotCleComparaison(hm);
            for(String fichier : r1.keySet()){
                if(r2.containsKey(fichier)){
                    resultat.put(new Fichier(fichier),r2.get(fichier).toString());
                }
            }
        }
        else{
            HashMap<String,String> r1 = rechercheMotCleComparaison(hm);
            for(String fichier : r1.keySet()){
                resultat.put(new Fichier(fichier),r1.get(fichier).toString());
            }
        }             
        r.setResultatsRequeteArguments(resultat);
        return r;      
    }

   
    private boolean getPolarite(String requete) {
        if(requete.contains("-")){
            return false;
        }
        else{
            return true;
        }
    }

    private int getOccurence(String requete) {
        String nb="";
        for(Character c : requete.toCharArray()){
            if(Character.isDigit(c)){
                nb+=c;
            }
        }
        if(nb.isEmpty()){
            nb="-1";
        }
        return Integer.valueOf(nb);
    }

    private HashMap<String, String> traitementRequete(String requete) {
        HashMap<String, String> retour = new HashMap<>();
        String motCle="";
        String occuranceETpolarite="";
        for(int i=0; i<requete.length(); i++){
            if(Character.isAlphabetic(requete.charAt(i))){
                while(requete.charAt(i)!=' '){
                    motCle+=requete.charAt(i);
                    if(i+1==requete.length()){
                        break;
                    }
                    else{
                        i++;
                    }
                }
                occuranceETpolarite="+";
            }
            else{
                while(!Character.isAlphabetic(requete.charAt(i))){
                    occuranceETpolarite+=requete.charAt(i);
                    if(i+1==requete.length()){
                        break;
                    }
                    else{
                        i++;
                    }
                }
                while(requete.charAt(i)!=' '){
                    motCle+=requete.charAt(i);
                    if(i+1==requete.length()){
                        break;
                    }
                    else{
                        i++;
                    }                    
                }
            }
            retour.put(motCle, occuranceETpolarite);
            motCle="";
            occuranceETpolarite="";
        }
        return retour;
    }

    private HashMap<String, Integer> rechercheMotCle(String motCle,Boolean polarite,int nbOccurrence) throws IllegalArgumentException{
        String res = MoteurTexteSimulation.rechercheMot(motCle);
        HashMap<String, Integer> requete = new HashMap<>();
        HashMap<String, Integer> resultat = new HashMap<>();
        toHashMap(requete, res);
        Iterator i = requete.entrySet().iterator();
        Map.Entry mapEntry;
        while(i.hasNext()){
            mapEntry = (Map.Entry) i.next();
            if(polarite){
                if((int)mapEntry.getValue()>=nbOccurrence){
                    resultat.put(mapEntry.getKey().toString(), (int)mapEntry.getValue());
                }
            }
            else{
                if((int)mapEntry.getValue()<nbOccurrence){
                    resultat.put(mapEntry.getKey().toString(), (int)mapEntry.getValue());
                }
            }                    
        }
        return resultat;
    }

    private HashMap<String, String> rechercheMotCleComparaison(HashMap<String, String> hm){
        int index = 1;
        HashMap<String, Integer> resultatMotCle1 = new HashMap<>();
        HashMap<String, Integer> resultatMotCle2 = new HashMap<>();
        HashMap<String, Integer> resultatMotCle3 = new HashMap<>();
        HashMap<String, String> resultat = new HashMap<>();
        for(String s : hm.keySet()){
            boolean polarite = this.getPolarite((hm.get(s)));
            int nbOccurrence = this.getOccurence((hm.get(s)));
            if(nbOccurrence==-1){
                if(index==1){
                    resultatMotCle1 = rechercheMotCleSansOccurrence(s,polarite);
                }                
                else if(index == 2){
                    resultatMotCle2=rechercheMotCleSansOccurrence(s,polarite);
                }
                else{
                    resultatMotCle3=rechercheMotCleSansOccurrence(s,polarite);
                }
            }
            else{
                if(index==1){
                    resultatMotCle1=rechercheMotCle(s, polarite, nbOccurrence);
                }
                else if(index==2){ 
                    resultatMotCle2=rechercheMotCle(s, polarite, nbOccurrence);
                }
                else{
                    resultatMotCle3=rechercheMotCle(s, polarite, nbOccurrence);
                }  
            }        
            index++;
        } 
        if(index-1==1){
            for(String s : resultatMotCle1.keySet()){
                resultat.put(s, resultatMotCle1.get(s).toString());
            }
        }
        else if(index-1==2){
            for(String s : resultatMotCle1.keySet()){
                if(resultatMotCle2.containsKey(s)){
                    resultat.put(s, resultatMotCle1.get(s)+" "+resultatMotCle2.get(s));
                }
            }
        }
        else{
            HashMap<String, String> intersection = new HashMap<>();
            for(String s : resultatMotCle1.keySet()){
                if(resultatMotCle2.containsKey(s)){
                    intersection.put(s, resultatMotCle1.get(s)+" "+resultatMotCle2.get(s));
                }
            }
            for(String s : intersection.keySet()){
                if(resultatMotCle3.containsKey(s)){
                    resultat.put(s, intersection.get(s)+" "+resultatMotCle3.get(s));
                }
            }
        }
        return resultat;
    }

    private HashMap<String, Integer> rechercheMotCleSansOccurrence(String motCle, boolean polarite) {
        HashMap<String, Integer> resultat = new HashMap<>();
        if(polarite){
            String r = MoteurTexteSimulation.rechercheMot(motCle);              
            toHashMap(resultat, r);
        }
        else{
            String r = MoteurTexteSimulation.rechercheMot(motCle); 
            File dossier = new File(System.getProperty("user.dir")+"/PFR2/C/DocTexte");
            for (int i = 0; i < dossier.listFiles().length; i++){
                if (dossier.listFiles()[i].isFile()&&!(r.contains(dossier.listFiles()[i].getName()))){
                    resultat.put(dossier.listFiles()[i].getName(), 0);                     
                } 
            }
        }
        return resultat;
    }

    public Recherche rechercheExemple(String requete) throws IllegalArgumentException{
        String nom="";
        String occurrence = "";
        boolean polarite = true;
        if(requete.contains("-")){
            polarite=false;
            requete = requete.substring(2);
        }
        else if(requete.contains("+")){
            requete = requete.substring(2);
        }
        for(int i =0;i<requete.length();i++){
            while(requete.charAt(i)!=' '){
                if(Character.isDigit(requete.charAt(i))){
                    occurrence+=requete.charAt(i);
                }
                else{
                    nom+=requete.charAt(i);
                }
                if(i+1==requete.length()){
                    break;
                }
                else{  
                    i++;
                }                
            }
        }      
        if(occurrence.isEmpty()){
            occurrence="0";
        }  
        Integer nbOccurrence = Integer.valueOf(occurrence);
        Fichier f = new Fichier(nom);
        Recherche r = new Recherche(f, "Recherche de similarité avec "+f.getName(), TypeRecherche.SIMILARITE);
        //verification de la presence du fichier
        //definir l'emplacement des fichiers textes
        if(this.cvf.fichierPresent(f,System.getProperty("user.dir")+"/PFR2/C/DocTexte")){            
            String s = MoteurTexteSimulation.rechercher(f);
            HashMap<String, Double> conversion = new HashMap<>();
            HashMap<String, Double> conversion_2 = new HashMap<>();
            HashMap<Fichier, Double> intersection = new HashMap<>();
            toHashMapSimilarite(conversion, s);
            if(this.multimoteur){
                String res = MoteurTexteSimulation.rechercher(f);                
                toHashMapSimilarite(conversion_2, res);            
                for(String fichier : conversion.keySet()){
                    if(conversion_2.containsKey(fichier)){
                        if(polarite){
                            if(conversion.get(fichier)>=nbOccurrence){
                                intersection.put(new Fichier(fichier),conversion.get(fichier));
                            }
                        }
                        else{
                            if(nbOccurrence == 0){
                                File dossierCourant = new File(System.getProperty("user.dir")+"/PFR2/C/DocTexte");
                                for (int i = 0; i < dossierCourant.listFiles().length; i++){
                                    if (dossierCourant.listFiles()[i].isFile()&&!dossierCourant.listFiles()[i].getName().equals(fichier)){
                                        intersection.put(new Fichier(dossierCourant.listFiles()[i].getAbsolutePath()),0.0);
                                    } 
                                }
                            }
                            else{
                                if(conversion.get(fichier)<nbOccurrence){
                                    intersection.put(new Fichier(fichier),conversion.get(fichier));
                                }
                            }
                        } 
                    }
                }               
            }
            else{
                for(String fichier : conversion.keySet()){
                    if(polarite){
                        if(conversion.get(fichier)>=nbOccurrence){
                            intersection.put(new Fichier(fichier),conversion.get(fichier));
                        }
                    }
                    else{
                        if(nbOccurrence == 0){
                            File dossierCourant = new File(System.getProperty("user.dir")+"/PFR2/C/DocTexte");
                            for (int i = 0; i < dossierCourant.listFiles().length; i++){
                                if (dossierCourant.listFiles()[i].isFile()&&!dossierCourant.listFiles()[i].getName().equals(fichier)){
                                    intersection.put(new Fichier(dossierCourant.listFiles()[i].getAbsolutePath()),0.0);
                                } 
                            }
                        }
                        else{
                            if(conversion.get(fichier)<nbOccurrence){
                                intersection.put(new Fichier(fichier),conversion.get(fichier));
                            }
                        }
                    }
                }
            }            
            r.setResultatsRequete(intersection);
        }
        else{
            throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
        }
        return r;      
    }

    private void toHashMap(HashMap<String, Integer> hm, String resultat){
        String titre = "";
        String occurrence ="";
        for(int i=0; i<resultat.length(); i++){
            //on recupère le titre du fichier texte retourné
            /*En supposant ici que le retour de la fonction c est de type
            nomDuFichier nbOccurrence */
            while(resultat.charAt(i)!=' '){
                titre += resultat.charAt(i);
                if(resultat.length()==i+1){ 
                    break;
                }
                else{
                    i++;
                }
            }
            if(i+1==resultat.length()){break;} 
            else{i++;}  
            if(Character.isDigit(resultat.charAt(i))){
                while(resultat.charAt(i)!=' '){
                    occurrence+=resultat.charAt(i); 
                    if(resultat.length()==i+1){ 
                        break;
                    }
                    else{
                        i++;
                    }                    
                }                   
            } 
            if(occurrence.isEmpty()){
                occurrence="-1";
            }      
            hm.put(titre, Integer.valueOf(occurrence));
            titre="";
            occurrence="";                    
        }
    }

    private void toHashMapSimilarite(HashMap<String, Double> hm, String resultat){
        String titre = "";
        String similarite="";
        for(int i=0; i<resultat.length(); i++){
            //on recupère le titre du fichier texte retourné
            /*En supposant ici que le retour de la fonction c est de type
            nomDuFichier similarite */
            while(resultat.charAt(i)!=' '){
                titre += resultat.charAt(i);
                i++;
            }            
            i++;
            if(Character.isDigit(resultat.charAt(i))){
                while(resultat.charAt(i)!=' '){
                    similarite+=resultat.charAt(i);
                    if(resultat.length()==i+1){ 
                        break;
                    }
                    else{
                        i++;
                    }                    
                }                   
            }      
            hm.put(titre, Double.parseDouble(similarite));
            titre="";
            similarite="";                  
        }
    }
}
