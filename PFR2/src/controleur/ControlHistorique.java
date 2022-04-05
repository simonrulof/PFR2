package controleur;

import java.sql.Date;
import java.util.Map;

import modele.bd.BDHistorique;
import modele.entite.Recherche;

public class ControlHistorique {
	private BDHistorique historique = BDHistorique.getInstance();

	public Map<String, Recherche> consulterHistorique()	{
		return historique.getHistoriqueRecherches();
	}
	
	public void ajoutRecherche(Recherche recherche) {		
		try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		historique.ajoutRecherche(recherche);
	}

	public void supprimerRecherche(Date d) {
		historique.supprimerRecherche(d);
	}

	public void supprimerRecherche(Recherche recherche) {
		historique.supprimerRecherche(recherche);
	}

	public void viderHistorique() {
		historique.viderHistorique();
	}

}
