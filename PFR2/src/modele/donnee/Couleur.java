package modele.donnee;

public enum Couleur {
    ROUGE,VERT,BLEU;

    public int getColonne(){
        if(this == ROUGE){
            return 0;
        }
        else if(this == VERT){
            return 1;
        }
        else{
            return 2;
        }
    }

    public static Couleur getCouleur(String couleur) {
        if(couleur.equals("rouge")||couleur.equals("ROUGE")||couleur.equals("R")||couleur.equals("r")){
            return Couleur.ROUGE;
        }
        else if(couleur.equals("vert")||couleur.equals("VERT")||couleur.equals("V")||couleur.equals("v")){
            return Couleur.VERT;
        }
        else if(couleur.equals("bleu")||couleur.equals("BLEU")||couleur.equals("B")||couleur.equals("b")){
            return Couleur.BLEU;
        }
        return null;
    }
}
