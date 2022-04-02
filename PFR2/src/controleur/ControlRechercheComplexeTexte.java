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
        if(requete.contains("/")||requete.contains(".")){
            return rechercheExemple(requete);
        }
        else{
            return rechercheMotCle(requete);
        }
    }

    private Recherche rechercheMotCle(String requete) throws IllegalArgumentException{   
        HashMap<String,String> hm = traitementRequete(requete);
        System.out.println(hm.toString());
        HashMap<String,String> res = new HashMap<>();
        HashMap<String,String> res_2 = new HashMap<>();
        HashMap<Fichier,String> resultat = new HashMap<>();
        HashMap<String, Integer> conversion = new HashMap<>();
        HashMap<String, Integer> conversion_2 = new HashMap<>();
        HashMap<String, Integer> intersection = new HashMap<>();
        Recherche r = new Recherche(new Fichier(".txt"), requete, TypeRecherche.MOTCLES);
        for(String s : hm.keySet()){
            if(!s.matches("[a-zA-Z]+")){
                //contient caractères spéciaux
                throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
            } 
        }
        if(this.multimoteur){
            rechercheMotCleComparaison(hm,conversion,conversion_2,intersection,res);
            rechercheMotCleComparaison(hm,conversion,conversion_2,intersection,res_2);
            for(String fichier : res.keySet()){
                if(res_2.containsKey(fichier)){
                    if(conversion.get(fichier)==null){
                        resultat.put(new Fichier(fichier),"");
                    }
                    else{
                        resultat.put(new Fichier(fichier),conversion.get(fichier).toString());
                    }
                }
            }
        }
        else{
            rechercheMotCleComparaison(hm,conversion,conversion_2,intersection,res);
            for(String fichier : res.keySet()){
                resultat.put(new Fichier(fichier),conversion.get(fichier).toString());
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
            if(nbOccurence==-1){
                rechercheMotCleSansOccurrence(hm,conversion,conversion_2,intersection,res);
                break;
            }
            else{
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
            }
            
            index++;
        } 
    }

    private void rechercheMotCleSansOccurrence(HashMap<String, String> hm, HashMap<String, Integer> conversion, HashMap<String, Integer> conversion_2, HashMap<String, Integer> intersection, HashMap<String, String> res) {
        int compteur = 1;
        String precedent = null;
        for(String s : hm.keySet()){
            if(precedent!=null){
                boolean polariteP = this.getPolarite(hm.get(precedent));
                boolean polariteA = this.getPolarite(hm.get(s));
                String r1 = CodeCTexte.rechercheMot(precedent);
                String r2 = CodeCTexte.rechercheMot(s);
                toHashMap(conversion, r1);
                toHashMap(conversion_2, r2);
                if(compteur==3){
                    if(polariteA){
                        for(String fichier : conversion_2.keySet()){
                            if(intersection.containsKey(fichier)){
                                res.put(fichier, conversion_2.get(fichier).toString());
                            }
                        }
                    }
                    else{
                        for(String fichier : conversion_2.keySet()){
                            if(!intersection.containsKey(fichier)){
                                res.put(fichier, conversion_2.get(fichier).toString());
                            }
                        }
                    }
                }
                else{
                    if(polariteA&&polariteP){
                        for(String fichier : conversion.keySet()){
                            if(conversion_2.containsKey(fichier)){
                                intersection.put(fichier, conversion.get(fichier));
                            }
                        }
                    }
                    else if(polariteA&&!polariteP){
                        for(String fichier : conversion.keySet()){
                            if(!conversion_2.containsKey(fichier)){
                                intersection.put(fichier, conversion.get(fichier));
                            }
                        }
                    }
                    else if(!polariteA&&polariteP){
                        for(String fichier : conversion_2.keySet()){
                            if(!conversion.containsKey(fichier)){
                                intersection.put(fichier, conversion_2.get(fichier));
                            }
                        }
                    }
                    else{
                        File dossier = new File(System.getProperty("user.dir"));
                        for (int i = 0; i < dossier.listFiles().length; i++){
                            if (dossier.listFiles()[i].isFile()&&!conversion.containsKey(dossier.listFiles()[i].getName())&&!conversion_2.containsKey(dossier.listFiles()[i].getName())){
                                intersection.put(dossier.listFiles()[i].getName(), 0); 
                                
                            } 
                        }
                    } 
                }
            }
            else{
                precedent=s;                
                boolean polariteP = this.getPolarite(hm.get(precedent));
                String r1 = CodeCTexte.rechercheMot(precedent);
                toHashMap(conversion, r1);
                if(polariteP){
                    for(String fichier : conversion.keySet()){
                        res.put(fichier, conversion.get(fichier).toString());
                    }
                }
                else{
                    File dossier = new File(System.getProperty("user.dir"));
                    for (int i = 0; i < dossier.listFiles().length; i++){
                        if (dossier.listFiles()[i].isFile()&&!conversion.containsKey(dossier.listFiles()[i].getName())){
                            res.put(dossier.listFiles()[i].getName(), "0");                     
                        } 
                    }
                }
                conversion = new HashMap<>();
            }
            compteur++;
        }
        if(compteur==2){
            for(String fichier : intersection.keySet()){
                res.put(fichier, intersection.get(fichier).toString());
            }
        }
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
                System.out.println("titre : "+titre); 
                if(resultat.length()==i+1){ 
                    break;
                }
                else{
                    i++;
                    System.out.println("index : "+i); 
                }
            }
            if(i+1==resultat.length()){break;} 
            else{i++;}  
            System.out.println("index : "+i); 
            if(Character.isDigit(resultat.charAt(i))){
                while(resultat.charAt(i)!=' '){
                    occurrence+=resultat.charAt(i);
                    System.out.println("occurrence : "+occurrence); 
                    if(resultat.length()==i+1){ 
                        break;
                    }
                    else{
                        i++;
                        System.out.println("index : "+i); 
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
