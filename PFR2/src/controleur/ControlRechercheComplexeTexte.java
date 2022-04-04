package controleur;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import codec.CodeCTexte;
import modele.donnee.TypeRecherche;
import modele.entite.Fichier;
import modele.entite.Recherche;

public class ControlRechercheComplexeTexte {
    private ControlVerificationFichiers cvf;
    private boolean multimoteur;

    public ControlRechercheComplexeTexte(ControlVerificationFichiers cvf, boolean multimoteur){
        this.cvf = cvf;
        this.multimoteur=multimoteur;
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
        String res = CodeCTexte.rechercheMot(motCle);
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
            String r = CodeCTexte.rechercheMot(motCle);              
            toHashMap(resultat, r);
        }
        else{
            String r = CodeCTexte.rechercheMot(motCle); 
            File dossier = new File(System.getProperty("user.dir"));
            for (int i = 0; i < dossier.listFiles().length; i++){
                if (dossier.listFiles()[i].isFile()&&!(r.contains(dossier.listFiles()[i].getName()))){
                    resultat.put(dossier.listFiles()[i].getName(), 0);                     
                } 
            }
        }
        return resultat;
    }

    public Recherche rechercheExemple(String nom) throws IllegalArgumentException{
        Fichier f = new Fichier(nom);
        Recherche r = new Recherche(f, "Recherche de similarité avec "+f.getName(), TypeRecherche.SIMILARITE);
        //verification de la presence du fichier
        if(this.cvf.fichierPresent(f)){            
            String s = CodeCTexte.rechercher(f);
            HashMap<String, Double> conversion = new HashMap<>();
            HashMap<String, Double> conversion_2 = new HashMap<>();
            HashMap<Fichier, Double> intersection = new HashMap<>();
            toHashMapSimilarite(conversion, s);
            if(this.multimoteur){
                String res = CodeCTexte.rechercher(f);                
                toHashMapSimilarite(conversion_2, res);            
                for(String fichier : conversion.keySet()){
                    if(conversion_2.containsKey(fichier)){
                        intersection.put(new Fichier(fichier),conversion.get(fichier));
                    }
                }               
            }
            else{
                for(String fichier : conversion.keySet()){
                    intersection.put(new Fichier(fichier),conversion.get(fichier));
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
