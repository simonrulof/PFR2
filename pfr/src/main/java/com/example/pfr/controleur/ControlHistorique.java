package com.example.pfr.controleur;

import java.util.Map;

import com.example.pfr.modele.bd.BDHistorique;
import com.example.pfr.modele.entite.Recherche;

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

	public void viderHistorique() {
		historique.viderHistorique();
	}

}
