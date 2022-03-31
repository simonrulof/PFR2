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
            HashMap<Fichier, Double> conversion = new HashMap<>();
            HashMap<Fichier, Double> conversion_res = new HashMap<>();
            HashMap<Fichier, Double> intersection = new HashMap<>();
            toHashMapSimilarite(conversion, s);
            if(this.multimoteur){
                String res = CodeCAudio.rechercher(f);                
                toHashMapSimilarite(conversion_res, res);            
            //PROBLEME: COMPARAISON ADRESSE FICHIER
            //faire le retour du toHashMap en string puis double
            //faire l'intersection puis tris
            //puis string to fichier avec le ifPresent et biCompute
                for(Fichier fichier : conversion.keySet()){
                    if(conversion_res.containsKey(fichier)){
                        intersection.put(fichier,conversion.get(fichier));
                    }
                }
                for(Fichier fichier : conversion_res.keySet()){
                    if(!intersection.containsKey(fichier)){
                        if(conversion.containsKey(fichier)){
                            intersection.put(fichier,conversion_res.get(fichier));
                        }
                    }
                }                
            }
            else{
                intersection = conversion;
            }            
            r.setResultatsRequete(intersection);
        }
        else{
            throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
        }
        return r;        
    }

    public Recherche rechercheOccurence(Fichier f, int nbOccurrence, boolean polarite) throws IllegalArgumentException{
        String resultat = CodeCAudio.rechercheOccurrence(f);
        HashMap<Fichier, Integer> conversion = new HashMap<>();
        String strPolarite="";
        //verification de la presence du fichier
        if(this.cvf.fichierPresent(f)){
            if(nbOccurrence>=0){
                toHashMap(conversion, resultat);
                //une fois les résultats de la recherche convertis en hashmap
                //on garde uniquement ceux qui correspondent à notre demamde (>=nbOccurrence)
                Iterator i = conversion.entrySet().iterator();
                Map.Entry mapEntry;
                while(i.hasNext()){
                    mapEntry = (Map.Entry) i.next();
                    if(polarite){
                        if(((Integer) mapEntry.getValue()).intValue()>nbOccurrence){
                            conversion.remove(mapEntry.getKey());
                        }
                        strPolarite=">";
                    }
                    else{
                        if(((Integer) mapEntry.getValue()).intValue()<nbOccurrence){
                            conversion.remove(mapEntry.getKey());
                        }
                        strPolarite="<";
                    }                    
                }
            }
            else if(nbOccurrence<0 || nbOccurrence==0 && polarite==false){
                throw new IllegalArgumentException("Le nombre d'occurrence ne convient pas");
            }   
        }
        else{
            throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
        }  
        Recherche r =  new Recherche(f,"recherche du fichier "+f.getName()+" avec "+strPolarite+nbOccurrence+" occurrences" , TypeRecherche.OCCURRENCE);     
        //r.setResultatsRequete();
        return r;
    }

    private void toHashMap(HashMap<Fichier, Integer> hm, String resultat){
        String titre = "";
        int occurrence=0;
        for(int i=0; i<resultat.length(); i++){
            if(resultat.charAt(i)==' '){
                //on recupère le titre du fichier texte retourné
                /*En supposant ici que le retour de la fonction c est de type
                nomDuFichier nbOccurrence\n */
                titre = "";
                while(resultat.charAt(i)!=' '){
                    titre += resultat.charAt(i);
                    i++;
                }
                if(Character.isDigit(resultat.charAt(i+1))){
                    occurrence = (int)resultat.charAt(i);
                }                
                hm.put(new Fichier(titre), occurrence);
            }
        }
    }

    private void toHashMapSimilarite(HashMap<Fichier, Double> hm, String resultat){
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
            hm.put(new Fichier(titre), Double.parseDouble(similarite));                  
        }
    }
}
