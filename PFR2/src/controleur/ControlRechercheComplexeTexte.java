package controleur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import codec.TexteCodeC;
import modele.entite.Texte;

public class ControlRechercheComplexeTexte {
    

    private String rechercheMotCle(String motCle,Boolean polarite) throws IllegalArgumentException{
        if(motCle.matches("[a-zA-Z0-9]+")){
            if(polarite){
                return TexteCodeC.rechercheMot(motCle);
            }
            else{
                return TexteCodeC.rechercheMotSans(motCle);
            }
        }
        else{
            //contient caractères spéciaux
            throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
        }        
    }

    //A COMPLETER
    private String rechercheMotCle(String motCle,Boolean polarite,String motCle2,Boolean polarite2) throws IllegalArgumentException{
        HashMap<String, Integer> resultat = new HashMap<>();
        if(motCle.matches("[a-zA-Z0-9]+")){
            String res="";
            String res2="";
            if(polarite){
                //on récupère le resultat de la recherche avec le motcle choisi
                res = TexteCodeC.rechercheMot(motCle);
            }
            else if(polarite==false){ 
                //on récupère le resultat sans le mot cle choisi               
                res = TexteCodeC.rechercheMotSans(motCle);
            }
            if(polarite2){  
                //idem avec le mot clé deux              
                res2 = TexteCodeC.rechercheMot(motCle2);
            }            
            else if(polarite2==false){ 
                //idem sans le mot cle deux               
                res2 = TexteCodeC.rechercheMotSans(motCle2);
            }
            //on crée une hashmap contenant les resultats des requetes
            HashMap<String, Integer> hashMapRes = new HashMap<>();
            HashMap<String, Integer> hashMapRes2 = new HashMap<>();
            toHashMap(hashMapRes, res);
            toHashMap(hashMapRes2, res2);
            //on fait une intersection entre les deux résultats
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
                        resultat.put((String)mapEntry.getKey(),(Integer)mapEntry.getValue());
                    }
                }
                else{
                    if(hashMapRes.containsKey(mapEntry.getKey())){
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

    //A COMPLETER
    private String rechercheMotCle(String motCle,Boolean polarite,String motCle2,Boolean polarite2,String motCle3,Boolean polarite3) throws IllegalArgumentException{
        HashMap<String, Integer> resultat = new HashMap<>();
        if(motCle.matches("[a-zA-Z0-9]+")){
            String res="";
            String res2="";
            String res3="";
            if(polarite){
                res = TexteCodeC.rechercheMot(motCle);
            }
            else if(polarite==false){                
                res = TexteCodeC.rechercheMotSans(motCle);
            }
            if(polarite2){                
                res2 = TexteCodeC.rechercheMot(motCle2);
            }            
            else if(polarite2==false){                
                res2 = TexteCodeC.rechercheMotSans(motCle2);
            }
            if(polarite3){                
                res3 = TexteCodeC.rechercheMot(motCle3);
            }            
            else if(polarite3==false){                
                res3 = TexteCodeC.rechercheMotSans(motCle3);
            }
            //on crée une hashmap contenant les resultats des requetes
            HashMap<String, Integer> hashMapRes = new HashMap<>();
            HashMap<String, Integer> hashMapRes2 = new HashMap<>();
            HashMap<String, Integer> hashMapRes3 = new HashMap<>();
            HashMap<String, Integer> intersection = new HashMap<>();
            toHashMap(hashMapRes, res);
            toHashMap(hashMapRes2, res2);
            toHashMap(hashMapRes3, res3);
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

    private String rechercheMotCle(String motCle, int nbOccurrence, Boolean polarite) throws IllegalArgumentException{
        String resultat = TexteCodeC.rechercheMot(motCle);
        HashMap<String, Integer> conversion = new HashMap<>();
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

    private String rechercheExemple(Texte f) throws IllegalArgumentException{
        //verification de la validite du fichier (extension...)
        String extension = f.getExtension();
        if(extension==".xml"|| extension ==".txt"){
            String titre = f.getChemin() + f.getTitre();
            //verification de la presence du fichier
            if(true){

            }
            else{
                throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
            }
            return TexteCodeC.rechercheExemple(titre);
        }
        else{
            throw new IllegalArgumentException("L'extension du fichier à chercher est incorrecte");
        }        
    }

    private void toHashMap(HashMap<String, Integer> hm, String resultat){
        int index = 0;
        String titre = "";
        for(int i=0; i<resultat.length(); i++){
            if(resultat.charAt(i)==':'){
                index = i+1;
                //on recupère le titre du fichier texte retourné
                /*En supposant ici que le retour de la fonction c est de type
                titre:nomDuFichier occurrence:nbOccurrence\n */
                titre = "";
                while(resultat.charAt(index)!=' '){
                    titre += resultat.charAt(index);
                    index++;
                }
                i=index;
                while(resultat.charAt(i)!=':'){
                    i++;
                }
                if(Character.isDigit(resultat.charAt(i+1))){
                    hm.put(titre, (int)(resultat.charAt(i+1)));
                }
            }
        }
    }
}
