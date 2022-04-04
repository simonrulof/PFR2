import java.io.File;

public class User {
    MoteurTexte moteurTexte = new MoteurTexte();
    MoteurImage moteurImage = new MoteurImage();
    MoteurAudio moteurAudio = new MoteurAudio();



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
        return false
    }

    public File[] comparer(String entree, boolean complexe, boolean multimoteur, boolean texte, boolean image, boolean audio){
        File[] fichiersTexte = new File[100];
        File[] fichiersImage = new File[100];
        File[] fichiersAudio = new File[100];
        if (complexe){
            if (texte){
                boolean motCle = isFile(entree;
                //ControlRechercheComplexeTexte(ControlVerificationFichiers cvf, boolean multimoteur)
            }
            if (image){
                //ControlRechercheComplexeImage(ControlVerificationFichiers cvf, boolean multimoteur)
            }
            if (audio){
                //ControlRechercheComplexeAudio(ControlVerificationFichiers cvf, boolean multimoteur)
            }
        }else{
            if (texte){
                boolean motCle = isFile(entree);
                if (motCle)
                    moteurTexte.comparermotcle(entree);
                else
                    moteurImage.comparer(entree);
                fichiersTexte = moteurTexte.getDerniereRecherche();
            }
            if (audio){
                moteurAudio.comparer(entree);
            }
            if (image){
                moteurImage.comparer(entree);
            }
        }
        File[] sortie = new File[300];
        int i = 0;
        int j = 0;
        int k = 0;
        for(i = 0; i < fichiersTexte.length; i++){
            sortie[i] = fichiersTexte[i];
        }
        for(j = i; j-i < fichiersTexte.length; j++){
            sortie[j] = fichiersImage[j-i];
        }
        for(k = j; k-j < fichiersTexte.length; k++){
            sortie[k] = fichiersAudio[k-j];
        }
        return sortie;
    }
}
