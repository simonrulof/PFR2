package test.testConsole;

import controleur.ControlRechercheComplexeAudio;
import controleur.ControlVerificationFichiers;
import modele.entite.Audio;
import modele.entite.Fichier;

public class TestRechercheAudio {
    public static void main(String[] args) {
		// Mise en place de l'environnement
        ControlVerificationFichiers cvf = new ControlVerificationFichiers();
        ControlRechercheComplexeAudio crca = new ControlRechercheComplexeAudio(cvf);
        Audio audio = new Audio("test.txt");

		// Verification de la bonne realisation du cas
		System.out.println("VERIFICATION");
        System.out.println(crca.recherche(audio));
        System.out.println(crca.rechercheOccurence(audio,0,true));
        
	}
}
