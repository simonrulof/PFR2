package modele;

import java.util.ArrayList;
import java.util.List;

public class Historique {
	private List<Recherche> historiqueRecherches = new ArrayList<>();

	public void viderHistorique() {
		historiqueRecherches.clear();
	}
	
	public void ajoutRecherche(Recherche r) {
		historiqueRecherches.add(r);
	}

	public void supprimerRecherche(Recherche r) {
		historiqueRecherches.remove(r);
	}

	public List<Recherche> getHistoriqueRecherches() {
		return historiqueRecherches;
	}

	public void setHistoriqueRecherches(List<Recherche> historiqueRecherches) {
		this.historiqueRecherches = historiqueRecherches;
	}

	
}