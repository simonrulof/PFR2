package test.testConsole;

import controleur.ControlRechercheComplexeImage;
import controleur.ControlVerificationFichiers;
import modele.donnee.Couleur;
import modele.entite.Fichier;
public class TestRechercheImage {
    public static void main(String[] args) {
		// Mise en place de l'environnement
        ControlVerificationFichiers cvf = new ControlVerificationFichiers();
        ControlRechercheComplexeImage crci = new ControlRechercheComplexeImage(cvf);
        Fichier image = new Fichier("test.bmp");

		// Verification de la bonne realisation du cas
		System.out.println("VERIFICATION");      
		System.out.println(crci.recherche(image));      
		System.out.println(crci.rechercheCouleur(1,Couleur.BLEU));     

	}
}
