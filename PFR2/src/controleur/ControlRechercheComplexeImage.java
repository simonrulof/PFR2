package controleur;

import java.io.File;
import java.util.HashMap;

import codec.CodeCImage;
import modele.donnee.Couleur;
import modele.donnee.TypeRecherche;
import modele.entite.Fichier;
import modele.entite.Recherche;

public class ControlRechercheComplexeImage {
    private ControlVerificationFichiers cvf;
    private boolean multimoteur;

    public ControlRechercheComplexeImage(ControlVerificationFichiers cvf, boolean multimoteur){
        this.cvf = cvf;
        this.multimoteur=multimoteur;
    }

    public Recherche rechercher(String requete){
        Recherche r;
        //si la requete contient un back slash alors elle porte sur un fichier
        if(requete.contains("/")||requete.contains(".")){
            r = recherche(requete);
        }
        //sinon il sagit d'un seuil et d'une couleur
        else{
            int seuil = getSeuil(requete);
            Couleur c = getCouleur(requete);
            r = rechercheCouleur(seuil, c);
        }
        r.setRequete(requete);
        return r;
    }

    private Couleur getCouleur(String requete) {
        String couleur = "";
        for(int i=0; i<requete.length(); i++){
            if(Character.isAlphabetic(requete.charAt(i))){
                couleur+=requete.charAt(i);
            }
        }
        return Couleur.getCouleur(couleur);
    }

    private int getSeuil(String requete) {
        String seuil = "";
        for(int i = 0; i<requete.length(); i++){
            if(Character.isDigit(requete.charAt(i))){
                seuil+=requete.charAt(i);
            }
            if(requete.charAt(i)=='-'){
                seuil = requete.charAt(i) + seuil;
            }
        }
        return Integer.valueOf(seuil);
    }

    private Recherche recherche(String requete) throws IllegalArgumentException{  
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
        //les chemins seront probablement à changer
        Fichier f = new Fichier(nom);
        Recherche r = new Recherche(f, "Recherche de similarité avec "+f.getName(), TypeRecherche.SIMILARITE);
        //definir l'emplacement des fichiers image
        if(this.cvf.fichierPresent(f,System.getProperty("user.dir"))){
            String s = CodeCImage.rechercher(f);
            HashMap<String, Double> conversion = new HashMap<>();
            HashMap<String, Double> conversion_2 = new HashMap<>();
            HashMap<Fichier, Double> intersection = new HashMap<>();
            toHashMapStringDouble(conversion, s);
            if(this.multimoteur){
                String res = CodeCImage.rechercher(f);                
                toHashMapStringDouble(conversion_2, res);            
                for(String fichier : conversion.keySet()){
                    if(conversion_2.containsKey(fichier)){
                        if(polarite){
                            if(conversion.get(fichier)>=nbOccurrence){
                                intersection.put(new Fichier(fichier),conversion.get(fichier));
                            }
                        }
                        else{
                            if(nbOccurrence == 0){
                                File dossierCourant = new File(System.getProperty("user.dir"));
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
                            File dossierCourant = new File(System.getProperty("user.dir"));
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

    private Recherche rechercheCouleur(int seuil, Couleur c){    
        Recherche r = new Recherche(new Fichier(".bmp"),"Recherche d'un seuil de "+c.name(), TypeRecherche.SEUIL);
        String s = CodeCImage.rechercher(seuil,c.getColonne());
        HashMap<String, Double> conversion = new HashMap<>();
        HashMap<String, Double> conversion_2 = new HashMap<>();
        HashMap<Fichier, Double> intersection = new HashMap<>();
        toHashMapStringDouble(conversion, s);
        if(this.multimoteur){
            String res = CodeCImage.rechercher(seuil,c.getColonne());                
            toHashMapStringDouble(conversion_2, res);          
            for(String fichier : conversion.keySet()){
                if(conversion_2.containsKey(fichier)&&conversion.get(fichier)>=seuil){
                    intersection.put(new Fichier(fichier),conversion.get(fichier));
                }
            }
        }
        else{
            for(String fichier : conversion.keySet()){
                if(conversion.get(fichier)>=seuil){
                    intersection.put(new Fichier(fichier),conversion.get(fichier));
                }
            }
        }
        r.setResultatsRequete(intersection);    
        return r;
    }

    private void toHashMapStringDouble(HashMap<String, Double> hm, String resultat){
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
