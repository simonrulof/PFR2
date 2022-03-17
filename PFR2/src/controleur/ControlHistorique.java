package controleur;

import java.util.List;
import java.util.Map;

import modele.bd.Historique;
import modele.entite.Recherche;

public class ControlHistorique {
	private Historique historique = new Historique();

	public Map<String, Recherche> consulterHistorique()
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
