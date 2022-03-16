package controleur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import codec.texteCodeC;
import entite.Texte;

public class rechercheComplexeTexte {
    

    private String rechercheMotCle(String motCle,Boolean polarite) throws IllegalArgumentException{
        if(motCle.matches("[a-zA-Z0-9]+")){
            if(polarite){
                return texteCodeC.rechercheMot(motCle);
            }
            else{
                return texteCodeC.rechecheMotSans(motCle);
            }
        }
        else{
            //contient caractères spéciaux
            throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
        }        
    }

    //A COMPLETER
    private String rechercheMotCle(String motCle,Boolean polarite,String motCle2,Boolean polarite2) throws IllegalArgumentException{
        if(motCle.matches("[a-zA-Z0-9]+")){
            if(polarite){
                return texteCodeC.rechercheMot(motCle);
            }
            else{
                return texteCodeC.rechecheMotSans(motCle);
            }
        }
        else{
            //contient caractères spéciaux
            throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
        }        
    }

    //A COMPLETER
    private String rechercheMotCle(String motCle,Boolean polarite,String motCle2,Boolean polarite2,String motCle3,Boolean polarite3) throws IllegalArgumentException{
        if(motCle.matches("[a-zA-Z0-9]+")){
            if(polarite){
                return texteCodeC.rechercheMot(motCle);
            }
            else{
                return texteCodeC.rechecheMotSans(motCle);
            }
        }
        else{
            //contient caractères spéciaux
            throw new IllegalArgumentException("Le mot clé contient un ou plusieurs caractères spéciaux");
        }        
    }

    private String rechercheMotCle(String motCle, int nbOccurrence) throws IllegalArgumentException{
        String resultat = texteCodeC.rechercheMot(motCle);
        HashMap<String, Integer> conversion = new HashMap<>();
        int index = 0;
        String titre = "";
        if(motCle.matches("[a-zA-Z0-9]+")){
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
                        conversion.put(titre, (int)(resultat.charAt(i+1)));
                    }
                }
            }
            //une fois les résultats de la recherche convertis en hashmap
            //on garde uniquement ceux qui correspondent à notre demamde (>=nbOccurrence)
            Iterator i = conversion.entrySet().iterator();
            Map.Entry mapEntry;
            while(i.hasNext()){
                mapEntry = (Map.Entry) i.next();
                if(((Integer) mapEntry.getValue()).intValue()>nbOccurrence){
                    conversion.remove(mapEntry.getKey());
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
            return texteCodeC.rechercheExemple(titre);
        }
        else{
            throw new IllegalArgumentException("L'extension du fichier à chercher est incorrecte");
        }        
    }
}
