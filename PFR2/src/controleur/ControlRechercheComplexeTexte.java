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

    public Recherche rechercheMotCle(String requete) throws IllegalArgumentException{
        if(requete.matches("[a-zA-Z0-9]+")){
            HashMap<String,String> hm = traitementRequete(requete);
            for(String s : hm.keySet()){
                boolean polarite = this.getPolarite((hm.get(s)));
                int nbOccurence = this.getOccurence((hm.get(s)));
                rechercheMotCle(s, polarite, nbOccurence);
            }
        }
        else{
            //contient caractères spéciaux
            throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
        } 
        //à la place de null mettre l'emplacement des fichiers texte indexés
        //pour montrer que cela porte sur tout les fichers textes
        return new Recherche(null, requete, TypeRecherche.MOTCLES) ;      
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

    public String rechercheMotCle(String motCle,Boolean polarite,int nbOccurence) throws IllegalArgumentException{
        HashMap<String, Integer> resultat = new HashMap<>();
        if(motCle.matches("[a-zA-Z0-9]+")){
            String res="";
            String res2="";
            String res3="";
            if(polarite){
                res = CodeCTexte.rechercheMot(motCle);
            }
            else if(polarite==false){                
                res = CodeCTexte.rechercheMotSans(motCle);
            }
            
            //on crée une hashmap contenant les resultats des requetes
            HashMap<String, Integer> hashMapRes = new HashMap<>();
            HashMap<String, Integer> hashMapRes2 = new HashMap<>();
            HashMap<String, Integer> hashMapRes3 = new HashMap<>();
            HashMap<String, Integer> intersection = new HashMap<>();
            //on fait une intersection entre les deux premiers résultats
            boolean hm = false;
            Iterator iterateur;
            if(hashMapRes.size()>hashMapRes2.size()){
                iterateur = hashMapRes.entrySet().iterator();
            }
            else{                
                iterateur = hashMapRes2.entrySet().iterator();
                hm = true;
            }
            Map.Entry mapEntry;
            while(iterateur.hasNext()){
                mapEntry = (Map.Entry) iterateur.next();
                if(hm){
                    if(hashMapRes2.containsKey(mapEntry.getKey())){
                        intersection.put((String)mapEntry.getKey(),(Integer)mapEntry.getValue());
                    }
                }
                else{
                    if(hashMapRes.containsKey(mapEntry.getKey())){
                        intersection.put((String)mapEntry.getKey(),(Integer)mapEntry.getValue());
                    }
                }
                
            }
            //maintenant on effectue une intersection entre la précédente et la dernière hashmap
            if(hashMapRes3.size()>intersection.size()){
                iterateur = hashMapRes3.entrySet().iterator();
                hm=false;
            }
            else{                
                iterateur = intersection.entrySet().iterator();
                hm = true;
            }
            while(iterateur.hasNext()){
                mapEntry = (Map.Entry) iterateur.next();
                if(hm){
                    if(hashMapRes3.containsKey(mapEntry.getKey())){
                        resultat.put((String)mapEntry.getKey(),(Integer)mapEntry.getValue());
                    }
                }
                else{
                    if(intersection.containsKey(mapEntry.getKey())){
                        resultat.put((String)mapEntry.getKey(),(Integer)mapEntry.getValue());
                    }
                }
                
            }
        }
        else{
            //contient caractères spéciaux
            throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
        } 
        return resultat.toString();       
    }

    public String rechercheMotCle(String motCle, int nbOccurrence, Boolean polarite) throws IllegalArgumentException{
        String resultat = CodeCTexte.rechercheMot(motCle);
        HashMap<Fichier, Integer> conversion = new HashMap<>();
        if(motCle.matches("[a-zA-Z0-9]+")){
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
                }
                else{
                    if(((Integer) mapEntry.getValue()).intValue()<nbOccurrence){
                        conversion.remove(mapEntry.getKey());
                    }
                }
                
            }
        }
        else{
            //contient des caractères spéciaux
            throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
        }        
        return conversion.toString();
    }

    public Recherche rechercheExemple(Fichier f) throws IllegalArgumentException{
        Recherche r = new Recherche(f, "Recherche de similarité avec "+f.getName(), TypeRecherche.SIMILARITE);
        //verification de la presence du fichier
        if(this.cvf.fichierPresent(f)){            
            String s = CodeCTexte.rechercher(f);
            HashMap<Fichier, Double> conversion = new HashMap<>();
            HashMap<Fichier, Double> conversion_res = new HashMap<>();
            HashMap<Fichier, Double> intersection = new HashMap<>();
            toHashMapSimilarite(conversion, s);
            if(this.multimoteur){
                String res = CodeCTexte.rechercher(f);                
                toHashMapSimilarite(conversion_res, res);
            //PROBLEME: COMPARAISON ADRESSE FICHIER
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

    private void toHashMap(HashMap<Fichier, Integer> hm, String resultat){
        String titre = "";
        for(int i=0; i<resultat.length(); i++){
            //on recupère le titre du fichier texte retourné
            /*En supposant ici que le retour de la fonction c est de type
            nomDuFichier nbOccurrence */
            titre += resultat.charAt(i);
            if(resultat.charAt(i)==' '){
                //attention pas de gestion si nb>9
                if(Character.isDigit(resultat.charAt(i+1))){
                    hm.put(new Fichier(titre), (int)(resultat.charAt(i+1)));
                    titre="";
                }
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
