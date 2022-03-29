package controleur;

import modele.bd.BDAdministrateur;

public class ControlIdentification {
    private BDAdministrateur bdadministrateur = BDAdministrateur.getInstance();
    
    public int sidentifier(String login, String mdp){
        return bdadministrateur.connexion(login,mdp);
    }

    public String visualiserBD(){
        return this.bdadministrateur.getAdmin().toString();
    }
}
