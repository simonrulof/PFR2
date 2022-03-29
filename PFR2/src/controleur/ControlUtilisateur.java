package controleur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import modele.bd.BDHistorique;
import modele.donnee.TypeFichier;
import modele.entite.Recherche;

public class ControlUtilisateur {    
    private BDHistorique historique = BDHistorique.getInstance();
    private ControlRechercheComplexeAudio crca;
    private ControlRechercheComplexeImage crci;
    private ControlRechercheComplexeTexte crct;
    private ControlOptionMode com;

    public ControlUtilisateur(ControlOptionMode com, ControlRechercheComplexeAudio crca, ControlRechercheComplexeImage crci, ControlRechercheComplexeTexte crct){
        this.crca=crca;
        this.crci=crci;
        this.crct=crct;
        this.com=com;
    }

    public boolean effectuerRecherche(Recherche r){
        if(!this.com.mode()){
            if(r.getTypeFichier()==TypeFichier.AUDIO){
                
            }
            else if(r.getTypeFichier()==TypeFichier.IMAGE){
    
            }
            else{
    
            }
        }
        else{
            System.out.println("Le mode ouvert n'est pas respecté");
        }        
        this.historique.ajoutRecherche(r);
        return false;
    }

    public boolean effectuerRecherche(Recherche r, Recherche r2){
        if(this.com.mode()){
            if(r.getTypeFichier()==TypeFichier.AUDIO){
            
            }
            else if(r.getTypeFichier()==TypeFichier.IMAGE){
    
            }
            else if(r.getTypeFichier()==TypeFichier.TEXTE){
    
            }
            if(r2.getTypeFichier()==TypeFichier.AUDIO){
    
            }
            else if(r2.getTypeFichier()==TypeFichier.IMAGE){
    
            }
            else{
    
            }
        }
        else{
            System.out.println("Le mode fermé n'est pas respecté");
        }        
        this.historique.ajoutRecherche(r);
        this.historique.ajoutRecherche(r2);
        return false;
    }

    public boolean effectuerRecherche(Recherche r, Recherche r2,Recherche r3){
        if(this.com.mode()){
            if(r.getTypeFichier()==TypeFichier.AUDIO){
            
            }
            else if(r.getTypeFichier()==TypeFichier.IMAGE){
    
            }
            else if(r.getTypeFichier()==TypeFichier.TEXTE){
    
            }
            if(r2.getTypeFichier()==TypeFichier.AUDIO){
    
            }
            else if(r2.getTypeFichier()==TypeFichier.IMAGE){
    
            }
            else{
                
            }
            if(r3.getTypeFichier()==TypeFichier.AUDIO){
    
            }
            else if(r3.getTypeFichier()==TypeFichier.IMAGE){
    
            }
            else{
                
            }
        }
        else{
            System.out.println("Le mode fermé n'est pas respecté");
        }
        this.historique.ajoutRecherche(r);
        this.historique.ajoutRecherche(r2);
        this.historique.ajoutRecherche(r3);
        return false;
    }

    public Map<String, Recherche> consulterHistorique(){
        return this.historique.getHistoriqueRecherches();
    }

    public void supprimerRecherche(Recherche r){
        this.historique.supprimerRecherche(r);
    }

    public void supprimerHistorique(){
        Iterator<Map.Entry<String, Recherche>> i = this.historique.getHistoriqueRecherches().entrySet().iterator();
        while(i.hasNext()) {
            i.remove();
        }        
    }

    public Map<String, Integer> toHashMapSeuil(String requete){        
        return null;
    }

    public Map<String, Integer> toHashMapMotCles(String requete){        
        Map<String, Integer> hm = new HashMap<>();
        int index = 0;
        String motCle = "";
        String nbOccurence="";
        for(int i=0; i<requete.length(); i++){
            if(requete.charAt(i)==':'){
                index = i+1;
                /*En supposant ici que la requete est de type
                motcle nbOccurrence; etc */
                motCle = "";
                nbOccurence="";
                while(requete.charAt(index)!=' '){
                    motCle += requete.charAt(index);
                    index++;
                }
                i=index+1;
                while(requete.charAt(i)!=';'){
                    if(Character.isDigit(requete.charAt(i))){   
                        nbOccurence+=requete.charAt(i);
                    }
                    else{
                        break;
                    }
                    i++;
                }
                hm.put(motCle, Integer.parseInt(nbOccurence));
            }
        }
        return hm;
    }

}
