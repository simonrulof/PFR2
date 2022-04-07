package com.example.pfr.modele.entite;

public class ResultatHolder {
    private ResultatRequete[] resultatRecherche = null;
    private String requeteEffectuee = null;
    private boolean isAdmin = false;
    private final static ResultatHolder INSTANCE = new ResultatHolder();

    private ResultatHolder(){}

    public static ResultatHolder getInstance(){
        return INSTANCE;
    }

    public void setResultatRecherche(ResultatRequete[] r){
        this.resultatRecherche = r;
    }

    public void setRequeteEffectuee(String r){
        this.requeteEffectuee = r;
    }

    public void changeAdmin(boolean value){
        this.isAdmin = value;
    }

    public boolean getAdmin(){
        return this.isAdmin;
    }

    public ResultatRequete[] getResultatRecherche(){
        return this.resultatRecherche;
    }

    public String getRequeteEffectuee(){
        return this.requeteEffectuee;
    }
}
