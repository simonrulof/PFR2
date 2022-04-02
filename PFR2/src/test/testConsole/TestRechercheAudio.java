package test.testConsole;

import controleur.ControlRechercheComplexeAudio;
import controleur.ControlVerificationFichiers;
import modele.entite.Recherche;

public class TestRechercheAudio {
    public static void main(String[] args) {
		// Mise en place de l'environnement
        ControlVerificationFichiers cvf = new ControlVerificationFichiers();
        ControlRechercheComplexeAudio crca = new ControlRechercheComplexeAudio(cvf,true);
        String requeteComparaison = "test.bin";
        String requeteOccurrence = "2 test.bin";

		// Verification de la bonne realisation du cas
		System.out.println("VERIFICATION");
        Recherche r = crca.rechercher(requeteComparaison);
        Recherche r2 = crca.rechercher(requeteOccurrence);
        
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
