package modele.entite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Historique {
	private Map<Recherche, String> historiqueRecherches = new HashMap<Recherche, String>();

	public void viderHistorique() {
		historiqueRecherches.clear();
	}
	
	public void ajoutRecherche(Recherche r) {
		String d = tempsToString(new Date(System.currentTimeMillis()));
		historiqueRecherches.put(r, d);
	}

	public void supprimerRecherche(Recherche r) {
		
		historiqueRecherches.remove(r);
	}

	public Map<Recherche, String> getHistoriqueRecherches() {
		return historiqueRecherches;
	}

	public void setHistoriqueRecherches(Map<Recherche, String> historiqueRecherches) {
		this.historiqueRecherches = historiqueRecherches;
	}

	private String tempsToString(Date d){
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		return formatter.format(d);
	}

	
}