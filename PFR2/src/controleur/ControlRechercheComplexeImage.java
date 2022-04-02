package controleur;

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
        if(requete.contains("/")){
            r = recherche(requete);
        }
        //sinon il sagit d'un seuil et d'une couleur
        else{
            int seuil = getSeuil(requete);
            Couleur c = getCouleur(requete);
            r = rechercheCouleur(seuil, c);
        }
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
        }
        return Integer.valueOf(seuil);
    }

    private Recherche recherche(String requete) throws IllegalArgumentException{  
        String nom="";
        for(int i =0;i<requete.length();i++){
            if(requete.charAt(i)!=' '){
                nom+=requete.charAt(i);
            }
        }
        //les chemins seront probablement à changer
        Fichier f = new Fichier(getClass().getClassLoader().getResource("./"+nom).getFile());
        Recherche r = new Recherche(f, "Recherche de similarité avec "+f.getName(), TypeRecherche.SIMILARITE);
        if(this.cvf.fichierPresent(f)){
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
                        intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion.get(fichier));
                    }
                }
            }
            else{
                for(String fichier : conversion.keySet()){
                    intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion.get(fichier));
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
        Fichier f = new Fichier(getClass().getClassLoader().getResource("./").getFile());
        Recherche r = new Recherche(f, "Recherche d'un seuil de couleur", TypeRecherche.SEUIL);
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
                    intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion.get(fichier));
                }
            }
        }
        else{
            for(String fichier : conversion.keySet()){
                if(conversion.get(fichier)>=seuil){
                    intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion.get(fichier));
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
            titre += resultat.charAt(i);
            if(resultat.charAt(i)==' '){
                i++;
                if(Character.isDigit(resultat.charAt(i))){
                    while(resultat.charAt(i)!=' '){
                        similarite+=resultat.charAt(i);
                        i++;
                    }                   
                }
                i--;
            }  
            hm.put(titre, Double.parseDouble(similarite));                  
        }
    }
}
