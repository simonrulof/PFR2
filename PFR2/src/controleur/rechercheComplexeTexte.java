package controleur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import codec.texteCodeC;

public class rechercheComplexeTexte {
    

    private String rechercheMotCle(String motCle) throws IllegalArgumentException{
        if(motCle.matches("[a-zA-Z0-9]+")){
            return texteCodeC.rechercheMot(motCle);
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

    private String rechercheExemple(String titre){
        return texteCodeC.rechercheExemple(titre);
    }
}
