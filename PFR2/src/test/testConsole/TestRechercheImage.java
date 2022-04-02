package test.testConsole;

import controleur.ControlRechercheComplexeImage;
import controleur.ControlVerificationFichiers;
import modele.entite.Recherche;
public class TestRechercheImage {
    public static void main(String[] args) {
		// Mise en place de l'environnement
        ControlVerificationFichiers cvf = new ControlVerificationFichiers();
        ControlRechercheComplexeImage crci = new ControlRechercheComplexeImage(cvf, true);
        String fichier = "test.bmp";
        String requete = "-10 B";

		// Verification de la bonne realisation du cas
		System.out.println("VERIFICATION"); 
		Recherche r = crci.rechercher(fichier); 
		Recherche r2 = crci.rechercher(requete); 

		System.out.println("Requete : " + r.getRequete());
        System.out.println("Fichier : " + r.getFichier());
        System.out.println("TypeFichier : " + r.getTypeFichier());
        System.out.println("Resultat basique : " + r.getResultats().toString());
        System.out.println("Resultats : " + r.getResultatsRequeteArguments().toString());
          
		System.out.println("Requete : " + r2.getRequete());
        System.out.println("Fichier : " + r2.getFichier());
        System.out.println("TypeFichier : " + r2.getTypeFichier());
        System.out.println("Resultat basique : " + r2.getResultats().toString());
        System.out.println("Resultats : " + r2.getResultatsRequeteArguments().toString());
	}
}
