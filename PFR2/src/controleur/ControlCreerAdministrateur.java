package controleur;

import modele.bd.BDAdministrateur;
import modele.entite.Administrateur;

public class ControlCreerAdministrateur {
    private static BDAdministrateur bdadministrateur = BDAdministrateur.getInstance();

    public void creerAdmin(String l, String mdp){
        Administrateur a = new Administrateur(l, mdp);
        bdadministrateur.ajouterAdmin(a);
    }

    public String visualiserBDAdmin(){
        return bdadministrateur.getAdmin().toString();
    }
}
