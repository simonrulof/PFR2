package test.testConsole;

import controleur.ControlHistorique;
import controleur.ControlRechercheComplexeTexte;
import controleur.ControlVerificationFichiers;
import modele.entite.Recherche;

public class TestHistorique {
    
    
    public static void main(String[] args) {
		// Mise en place de l'environnement
        ControlVerificationFichiers cvf = new ControlVerificationFichiers();
        ControlRechercheComplexeTexte crct = new ControlRechercheComplexeTexte(cvf, true);
        ControlHistorique ch = new ControlHistorique();
        String requete1 = "+ Football 4 mortel";
        String requete2 = "- Football - mortel - machine";

		// Verification de la bonne realisation du cas
		System.out.println("VERIFICATION"); 
		Recherche r = crct.rechercher(requete1); 
		Recherche r2 = crct.rechercher(requete2);
        
        ch.ajoutRecherche(r);
        ch.ajoutRecherche(r2);
        System.out.println("Historique: "+ch.consulterHistorique());
        ch.supprimerRecherche(r);        
        System.out.println("Historique: "+ch.consulterHistorique());
        ch.ajoutRecherche(r2);
        System.out.println("Historique: "+ch.consulterHistorique());
	}
}
