package Model;

import java.io.File;
import Moteurs.*;
public class User {
    private MoteurTexte moteurTexte = new MoteurTexte();
    private MoteurImage moteurImage = new MoteurImage();
    private MoteurAudio moteurAudio = new MoteurAudio();

    private User(){}

    private static class UserHolder{
        private static final User instance = new User();
    }

    public static User getInstance(){
        return UserHolder.instance;
    }


    private boolean isFile(String entree){
        for(int i = 0; i < entree.length(); i++){
            if (entree.charAt(i) == '/') return true;
        }
        return false;
    }

    public Recherche[] comparer(String entree, boolean complexe, boolean multimoteur, boolean texte, boolean image, boolean audio){
        Recherche[] fichiersTexte = new Recherche[100];
        Recherche[] fichiersImage = new Recherche[100];
        Recherche[] fichiersAudio = new Recherche[100];
        if (complexe){
            if (texte){
                boolean motCle = isFile(entree);
                //ControlRechercheComplexeTexte(ControlVerificationFichiers cvf, boolean multimoteur)
            }
            if (image){
                //ControlRechercheComplexeImagemoteurImage(ControlVerificationFichiers cvf, boolean multimoteur)
            }
            if (audio){
                //ControlRechercheComplexeAudio(ControlVerificationFichiers cvf, boolean multimoteur)
            }
        }else{
            if (texte){
                boolean isFile = isFile(entree);
                if (isFile) {
                    moteurTexte.comparerFichier(new File(entree));
                }
                else {
                    String mots[] = entree.split(" ");
                    moteurTexte.comparerFichier(mots);
                }
                return moteurTexte.getDerniereRecherche();
            }
            if (image){
                moteurImage.comparerFichier(new File(entree));
                return moteurImage.getDerniereRecherche();
            }
            if (audio){
                moteurAudio.comparerFichier(new File(entree));
                return moteurAudio.getDerniereRecherche();
            }
        }
        return null;
    }
}
