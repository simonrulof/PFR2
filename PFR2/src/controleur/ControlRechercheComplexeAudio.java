package controleur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import codec.CodeCAudio;
import modele.entite.Audio;

public class ControlRechercheComplexeAudio {
    
    private ControlVerificationFichiers cvf;

    public ControlRechercheComplexeAudio(ControlVerificationFichiers cvf){
        this.cvf = cvf;
    }

    private String recherche(Audio f) throws IllegalArgumentException{
        //verification de la validite du fichier (extension...)
        String extension = f.getExtension();
        if(extension==".wav"|| extension ==".bin"){
            String titre = f.getChemin() + f.getTitre();
            //verification de la presence du fichier
            if(this.cvf.fichierPresent(f)){

            }
            else{
                throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
            }
            return CodeCAudio.recherche(titre);
        }
        else{
            throw new IllegalArgumentException("L'extension du fichier à chercher est incorrecte");
        }        
    }

    private String rechercheOccurence(Audio f, int nbOccurrence, boolean polarite) throws IllegalArgumentException{
        String descripteur = CodeCAudio.descripteur(f);
        HashMap<String, Double> conversion = new HashMap<>();
        //verification de la validite du fichier (extension...)
        String extension = f.getExtension();
        if(extension==".wav"|| extension ==".bin"){
            String titre = f.getChemin() + f.getTitre();
            //verification de la presence du fichier
            if(this.cvf.fichierPresent(f)){

            }
            else{
                throw new IllegalArgumentException("Le fichier n'est pas présent à l'endroit indiqué");
            }
            if(nbOccurrence>=0){
                toHashMap(conversion, descripteur);
                //une fois les résultats de la recherche convertis en hashmap
                //on garde uniquement ceux qui correspondent à notre demamde (>=nbOccurrence)
                Iterator i = conversion.entrySet().iterator();
                Map.Entry mapEntry;
                while(i.hasNext()){
                    mapEntry = (Map.Entry) i.next();
                    if(polarite){
                        //En vrai ici ça ne fonctionne pas parce que en C on retourne un pourcentage de similarité
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
            else if(nbOccurrence<0 || nbOccurrence==0 && polarite==false){
                throw new IllegalArgumentException("Le nombre d'occurrence ne convient pas");
            }            
        }
        else{
            throw new IllegalArgumentException("L'extension du fichier à chercher est incorrecte");
        }   
        return conversion.toString();     
    }

    private void toHashMap(HashMap<String, Double> hm, String resultat){
        int index = 0;
        String titre = "";
        String similarite = "";
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
                    while(resultat.charAt(i)!='\n'){
                        similarite += resultat.charAt(i);
                        i++;
                    }
                }
                else{
                    //probleme de syntaxe dans le retour de la requete en C
                }
                hm.put(titre, Double.parseDouble(similarite));
            }
        }
    }
}
