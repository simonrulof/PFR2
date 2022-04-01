package controleur;

import java.util.Map;

import modele.bd.BDHistorique;
import modele.entite.Recherche;

public class ControlHistorique {
	private BDHistorique historique = BDHistorique.getInstance();

	public Map<String, Recherche> consulterHistorique()	{
		return historique.getHistoriqueRecherches();
	}
	
	public void ajoutRecherche(Recherche recherche) {
		historique.ajoutRecherche(recherche);
	}

	public void supprimerRecherche(Recherche recherche) {
		historique.supprimerRecherche(recherche);
	}

	public void viderHistorique() {
		historique.viderHistorique();
	}

}
