package modele.donnee;

public enum Couleur {
    ROUGE,VERT,BLEU;

    public int getColonne(){
        if(this == ROUGE){
            return 1;
        }
        else if(this == VERT){
            return 2;
        }
        else{
            return 3;
        }
    }
}
