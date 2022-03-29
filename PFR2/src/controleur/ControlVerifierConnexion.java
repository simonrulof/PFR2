package controleur;

import modele.bd.BDAdministrateur;
import modele.entite.Administrateur;

public class ControlVerifierConnexion {
    private BDAdministrateur bdAdmin = BDAdministrateur.getInstance();
    
    public boolean verifierConnexion(int numProfil){
        Administrateur a= this.bdAdmin.trouver(numProfil);;
        boolean connexion=false;
        if(a!=null){
            connexion=a.getConnecte();
        }
        return connexion;
    }
    
}
