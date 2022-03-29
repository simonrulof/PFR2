package test.testConsole;

import controleur.ControlRechercheComplexeTexte;
import controleur.ControlVerificationFichiers;
import modele.entite.Texte;

public class TestRechercheTexte {
    public static void main(String[] args) {
		// Mise en place de l'environnement
        ControlVerificationFichiers cvf = new ControlVerificationFichiers();
        ControlRechercheComplexeTexte crct = new ControlRechercheComplexeTexte(cvf);
        Texte texte = new Texte("test.txt");

		// Verification de la bonne realisation du cas
		System.out.println("VERIFICATION");
        System.out.println(crct.rechercheExemple(texte));
        System.out.println(crct.rechercheMotCle("test", 2, true));
        System.out.println(crct.rechercheMotCle("test", true));
        System.out.println(crct.rechercheMotCle("test", true,"test2",false));
        System.out.println(crct.rechercheMotCle("test", true,"test2",false,"test3",true));
        //System.out.println(crct.rechercheMotCle("fl@", true));       

	}
}
