package controleur;

import java.util.List;
import java.util.Map;

import modele.entite.Historique;
import modele.entite.Recherche;

public class CHistorique {
	private Historique historique = new Historique();

	public Map<Recherche, String> consulterHistorique()
	{
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
