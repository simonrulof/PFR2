package controleur;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import codec.CodeCAudio;
import modele.donnee.TypeRecherche;
import modele.entite.Fichier;
import modele.entite.Recherche;

public class ControlRechercheComplexeAudio {    
    private ControlVerificationFichiers cvf;
    private boolean multimoteur;

    public ControlRechercheComplexeAudio(ControlVerificationFichiers cvf, boolean multimoteur){
        this.cvf = cvf;
        this.multimoteur=multimoteur;
    }

    public Recherche recherche(Fichier f) throws IllegalArgumentException{
        Recherche r = new Recherche(f, "Recherche de similarité avec "+f.getName(), TypeRecherche.SIMILARITE);
        //verification de la presence du fichier
        if(this.cvf.fichierPresent(f)){            
            String s = CodeCAudio.rechercher(f);
            HashMap<String, Double> conversion = new HashMap<>();
            HashMap<String, Double> conversion_2 = new HashMap<>();
            HashMap<Fichier, Double> intersection = new HashMap<>();
            toHashMapStringDouble(conversion, s);
            if(this.multimoteur){
                String res = CodeCAudio.rechercher(f);                
                toHashMapStringDouble(conversion_2, res);            
                for(String fichier : conversion.keySet()){
                    if(conversion_2.containsKey(fichier)){
                        intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion.get(fichier));
                    }
                }
                for(String fichier : conversion_2.keySet()){
                    if(conversion.containsKey(fichier)){
                        intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion_2.get(fichier));
                    }
                }                
            }
            else{
                for(String fichier : conversion.keySet()){
                    intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion_2.get(fichier));
                }
            }            
            r.setResultatsRequete(intersection);
        }
        else{
            throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
        }
        return r;        
    }

    public Recherche rechercheOccurence(String requete) throws IllegalArgumentException{
        String fichier="";
        for(int i =0;i<requete.length();i++){
            if(requete.charAt(i)!=' '){
                fichier+=requete.charAt(i);
            }
        }
        Fichier f = new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile());
        String resultat = CodeCAudio.rechercheOccurrence(f);
        int nbOccurrence = getOccurence(requete);
        boolean polarite = getPolarite(requete);
        HashMap<String, String> conversion = new HashMap<>();
        HashMap<String, String> conversion_2 = new HashMap<>();
        HashMap<Fichier, String> intersection = new HashMap<>();
        Recherche r =  new Recherche(f,"" , TypeRecherche.OCCURRENCE);     
        //verification de la presence du fichier
        if(this.cvf.fichierPresent(f)){
            if(this.multimoteur){
                comparaisonOccurrenceResultat(nbOccurrence, polarite, conversion_2,r,resultat);
                comparaisonOccurrenceResultat(nbOccurrence, polarite, conversion, r, resultat);
                //l'intersection
                for(String s : conversion.keySet()){
                    if(conversion_2.containsKey(fichier)){
                        intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion.get(fichier));
                    }
                }
                for(String s : conversion_2.keySet()){
                    if(conversion.containsKey(fichier)){
                        intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion_2.get(fichier));
                    }
                }
            }
            else{
                comparaisonOccurrenceResultat(nbOccurrence, polarite, conversion,r,resultat); 
                for(String s : conversion.keySet()){
                    intersection.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion_2.get(fichier));
                }           
            }                
            r.setResultatsRequeteTemps(intersection);
        }
        else{
            throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
        }  
        return r;
    }

    private void comparaisonOccurrenceResultat(int nbOccurrence, boolean polarite, HashMap<String, String> hm, Recherche r, String resultat){
        String strPolarite="";
        if(nbOccurrence<0 || nbOccurrence==0 && polarite==false){
            throw new IllegalArgumentException("Le nombre d'occurrence ne convient pas");
        }
        else if(nbOccurrence>=0){
            toHashMapString(hm, resultat);
            //une fois les résultats de la recherche convertis en hashmap
            //on garde uniquement ceux qui correspondent à notre demamde (>=nbOccurrence)
            Iterator i = hm.entrySet().iterator();
            Map.Entry mapEntry;
            while(i.hasNext()){
                mapEntry = (Map.Entry) i.next();
                if(polarite){
                    if((getOccurenceSansTemps(mapEntry.getValue().toString()))>nbOccurrence){
                        hm.remove(mapEntry.getKey());
                    }
                    strPolarite=">";
                }
                else{
                    if((getOccurenceSansTemps(mapEntry.getValue().toString()))<nbOccurrence){
                        hm.remove(mapEntry.getKey());
                    }
                    strPolarite="<";
                }                    
            }
        }
        r.setRequete("recherche du fichier "+r.getFichier().getName()+" avec "+strPolarite+nbOccurrence+" occurrences");
    }

    private int getOccurenceSansTemps(String valeur) {
        String nb="";
        for(int i=0; i<valeur.length(); i++){
            if(Character.isDigit(valeur.charAt(i))){
                nb+=valeur.charAt(i);
            }
            else if(valeur.charAt(i)==' '){
                break;
            }
        }
        return Integer.valueOf(nb);
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
            nb="0";
        }
        return Integer.valueOf(nb);
    }

    private void toHashMapString(HashMap<String, String> hm, String resultat){
        String titre = "";
        String occurrence="";
        for(int i=0; i<resultat.length(); i++){
            if(resultat.charAt(i)==' '){
                //on recupère le titre du fichier texte retourné
                /*En supposant ici que le retour de la fonction c est de type
                nomDuFichier nbOccurrence marqueurTemps\n */
                titre = "";
                while(resultat.charAt(i)!=' '){
                    titre += resultat.charAt(i);
                    i++;
                }
                i++;
                if(Character.isDigit(resultat.charAt(i))){
                    while(!Character.isAlphabetic(resultat.charAt(i))){
                        occurrence += resultat.charAt(i);
                        i++;
                    }                        
                }                
                hm.put(titre, occurrence);
            }
        }
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
                //attention pas de gestion si nb>9
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
