package modele.entite;

import java.io.File;
import controleur.ControlRechercheComplexeAudio;
import controleur.ControlRechercheComplexeImage;
import controleur.ControlRechercheComplexeTexte;
import controleur.ControlVerificationFichiers;
import modele.moteur.*;

public class Utilisateur {
    private Moteur moteurTexte = new MoteurTexte();
    private MoteurImage moteurImage = new MoteurImage();
    private MoteurAudio moteurAudio = new MoteurAudio();
    private ControlVerificationFichiers cvf = new ControlVerificationFichiers();

    private Utilisateur(){}

    private static class UtilisateurHolder{
        private static final Utilisateur instance = new Utilisateur();
    }

    public static Utilisateur getInstance(){
        return UtilisateurHolder.instance;
    }


    private boolean isFile(String entree){
        for(int i = 0; i < entree.length(); i++){
            if (entree.charAt(i) == '/') return true;
        }
        return false;
    }

    private boolean isComplex(String entree){
        for(int i = 0; i < entree.length(); i++){
            if (entree.charAt(i) == '+' || entree.charAt(i) == ' ' || entree.charAt(i) == '-'){
                return true;
            }
        }
        return false;
    }
    public ResultatRequete[] comparer(String requete, boolean multimoteur, boolean texte, boolean image, boolean audio){
        ResultatRequete[] fichiersTexte = new ResultatRequete[100];
        ResultatRequete[] fichiersImage = new ResultatRequete[100];
        ResultatRequete[] fichiersAudio = new ResultatRequete[100];

        Recherche recherche;

        if (isComplex(requete)){
            if (texte){
                boolean motCle = isFile(requete);
                ControlRechercheComplexeTexte crct = new ControlRechercheComplexeTexte(this.cvf,multimoteur);
                recherche = crct.rechercher(requete);
            }
            else if(image){
                ControlRechercheComplexeImage crci = new ControlRechercheComplexeImage(this.cvf, multimoteur);
                recherche = crci.rechercher(requete);            
            }
            else if(audio){
                ControlRechercheComplexeAudio crca = new ControlRechercheComplexeAudio(this.cvf, multimoteur);
                recherche = crca.rechercher(requete);
            }
        }else{
            if (texte){
                boolean isFile = isFile(requete);
                if (isFile) {
                    moteurTexte.comparerFichier(new File(requete));
                }
                else {
                    String mots[] = requete.split(" ");
                    moteurTexte.comparerFichier(mots);
                }
                return moteurTexte.getDerniereRecherche();
            }
            if (image){
                moteurImage.comparerFichier(new File(requete));
                return moteurImage.getDerniereRecherche();
            }
            if (audio){
                moteurAudio.comparerFichier(new File(requete));
                return moteurAudio.getDerniereRecherche();
            }
        }
        return null;
    }
}
