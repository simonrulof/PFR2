package controleur;

import codec.CodeCAdministrateur;
import codec.CodeCMoteur;
import modele.entite.Fichier;

public class ControlAdministrateur extends ControlUtilisateur{
    private ControlVerifierConnexion cvc;

    public ControlAdministrateur(ControlVerifierConnexion cvc,ControlOptionMode com, ControlRechercheComplexeAudio crca, ControlRechercheComplexeImage crci, ControlRechercheComplexeTexte crct){
        super(com,crca,crci,crct);
        this.cvc = cvc;
    }

    public boolean ajouter(Fichier f) {
        return CodeCAdministrateur.ajouter(f);
    }

    public boolean suppression(Fichier f){
        return CodeCAdministrateur.supprimer(f);
    }
    
    public boolean reIndexer(Fichier f){
        return CodeCMoteur.indexer(f);
    }

    public boolean reIndexerTout(){
        return CodeCMoteur.indexerTout();
    }

    public void modifierConfiguration(){

    }


}
