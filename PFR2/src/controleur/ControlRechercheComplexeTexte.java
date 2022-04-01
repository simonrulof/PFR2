package controleur;

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

    public Recherche recherche(String requete){
        if(requete.contains("/")){
            return rechercheExemple(requete);
        }
        else{
            return rechercheMotCle(requete);
        }
    }

    private Recherche rechercheMotCle(String requete) throws IllegalArgumentException{   
        HashMap<String,String> hm = traitementRequete(requete);
        HashMap<String,String> res = new HashMap<>();
        HashMap<String,String> res_2 = new HashMap<>();
        HashMap<Fichier,String> resultat = new HashMap<>();
        HashMap<String, Integer> conversion = new HashMap<>();
        HashMap<String, Integer> conversion_2 = new HashMap<>();
        HashMap<String, Integer> intersection = new HashMap<>();
        Recherche r = new Recherche(null, requete, TypeRecherche.MOTCLES);
        for(String s : hm.keySet()){
            if(!s.matches("[a-zA-Z0-9]+")){
                //contient caractères spéciaux
                throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
            } 
        }
        if(this.multimoteur){
            rechercheMotCleComparaison(hm,conversion,conversion_2,intersection,res);
            rechercheMotCleComparaison(hm,conversion,conversion_2,intersection,res_2);
            for(String fichier : res.keySet()){
                if(res_2.containsKey(fichier)){
                    resultat.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion.get(fichier).toString());
                }
            }
        }
        else{
            rechercheMotCleComparaison(hm,conversion,conversion_2,intersection,res);
            for(String fichier : res.keySet()){
                resultat.put(new Fichier(getClass().getClassLoader().getResource("./"+fichier).getFile()),conversion.get(fichier).toString());
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
            nb="0";
        }
        return Integer.valueOf(nb);
    }

    private HashMap<String, String> traitementRequete(String requete) {
        HashMap<String, String> retour = new HashMap<>();
        String motCle="";
        String occuranceETpolarite="";
        for(int i=0; i<requete.length(); i++){
            if((requete.charAt(i) >= 'a' && requete.charAt(i) <= 'z') || (requete.charAt(i) >= 'A' && requete.charAt(i) <= 'Z')){
                while(requete.charAt(i)!=' '){
                    motCle+=requete.charAt(i);
                    i++;
                }
                occuranceETpolarite="+";
            }
            else{
                while(!((requete.charAt(i) >= 'a' && requete.charAt(i) <= 'z') || (requete.charAt(i) >= 'A' && requete.charAt(i) <= 'Z'))){
                    occuranceETpolarite+=requete.charAt(i);
                    i++;
                }
                while(requete.charAt(i)!=' '){
                    motCle+=requete.charAt(i);
                    i++;
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
        toHashMap(requete, res);
        Iterator i = requete.entrySet().iterator();
        Map.Entry mapEntry;
        while(i.hasNext()){
            mapEntry = (Map.Entry) i.next();
            if(polarite){
                if((int)mapEntry.getValue()<nbOccurrence){
                    requete.remove(mapEntry.getKey());
                }
            }
            else{
                if((int)mapEntry.getValue()>=nbOccurrence){
                    requete.remove(mapEntry.getKey());
                }
            }                    
        }
        return requete;
    }

    private void rechercheMotCleComparaison(HashMap<String, String> hm, HashMap<String, Integer> conversion, HashMap<String, Integer> conversion_2, HashMap<String, Integer> intersection, HashMap<String, String> res){
        int compteur = hm.size();
        int index = 0;
        for(String s : hm.keySet()){
            boolean polarite = this.getPolarite((hm.get(s)));
            int nbOccurence = this.getOccurence((hm.get(s)));
            if(compteur==1){
                conversion=rechercheMotCle(s, polarite, nbOccurence);
                for(String fichier : conversion.keySet()){
                    //changer dans la fonction de conversion avec le biCompute
                    res.put(fichier, conversion.get(fichier).toString());
                }
                break;
            }
            else if(compteur==2){ 
                if(index==0){
                    conversion=rechercheMotCle(s, polarite, nbOccurence);
                }  
                else{
                    conversion_2=rechercheMotCle(s, polarite, nbOccurence);
                    for(String fichier : conversion.keySet()){
                        if(conversion_2.containsKey(fichier)){
                            //changer dans la fonction de conversion avec le biCompute
                            res.put(fichier,conversion.get(fichier).toString());
                        }
                    }
                }              
            }
            else if(compteur==3){
                if(index==0){
                    conversion=rechercheMotCle(s, polarite, nbOccurence);
                } 
                else if(index ==1){
                    conversion_2=rechercheMotCle(s, polarite, nbOccurence);
                    for(String fichier : conversion.keySet()){
                        if(conversion_2.containsKey(fichier)){
                            intersection.put(fichier,conversion.get(fichier));
                        }
                    }
                } 
                else{
                    conversion_2 = new HashMap<>();
                    conversion_2=rechercheMotCle(s, polarite, nbOccurence);
                    for(String fichier : intersection.keySet()){
                        if(conversion_2.containsKey(fichier)){
                            //changer dans la fonction de conversion avec le biCompute
                            res.put(fichier,conversion.get(fichier).toString());
                        }
                    }
                }
            }
            index++;
        } 
    }

    public Recherche rechercheExemple(String nom) throws IllegalArgumentException{
        Fichier f = new Fichier(getClass().getClassLoader().getResource("./"+nom).getFile());
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

    private void toHashMap(HashMap<String, Integer> hm, String resultat){
        String titre = "";
        String occurrence ="";
        for(int i=0; i<resultat.length(); i++){
            //on recupère le titre du fichier texte retourné
            /*En supposant ici que le retour de la fonction c est de type
            nomDuFichier nbOccurrence */
            titre += resultat.charAt(i);
            if(resultat.charAt(i)==' '){
                //attention pas de gestion si nb>9
                while(Character.isDigit(resultat.charAt(i+1))){
                    occurrence+=resultat.charAt(i+1);
                    i++;
                }
            }
            hm.put(titre, Integer.valueOf(occurrence));
            occurrence="";
            titre="";                    
        }
    }

    private void toHashMapSimilarite(HashMap<String, Double> hm, String resultat){
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
