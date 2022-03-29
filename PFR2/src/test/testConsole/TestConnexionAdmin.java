package test.testConsole;

import controleur.ControlCreerAdministrateur;
import controleur.ControlIdentification;
import controleur.ControlVerifierConnexion;
import vue.vueconsole.BoundaryIdentification;

public class TestConnexionAdmin {
    public static void main(String[] args) {
		// Mise en place de l'environnement
		ControlCreerAdministrateur cca = new ControlCreerAdministrateur();
		cca.creerAdmin("123", "123");

		// Initialisation controleur du cas & cas Inclus/etendu
		ControlIdentification ci = new ControlIdentification();
        ControlVerifierConnexion cvc = new ControlVerifierConnexion();
		// Initialisation vue du cas
		BoundaryIdentification bi = new BoundaryIdentification(ci);
		// Lancement du cas
		int numAdmin = bi.identification();
        boolean identificationAdmin1 = cvc.verifierConnexion(numAdmin);

		// Verification de la bonne realisation du cas
		System.out.println("VERIFICATION");
		System.out.println("numero de l'admin = " + numAdmin);
		System.out.println(ci.visualiserBD());
        System.out.println("Identification admin: "+identificationAdmin1);

	}
}
