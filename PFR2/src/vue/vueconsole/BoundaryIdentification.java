package vue.vueconsole;

import java.util.Scanner;

import controleur.ControlIdentification;

public class BoundaryIdentification {
    public Scanner sc=new Scanner(System.in);
    private ControlIdentification ci;

    public BoundaryIdentification(ControlIdentification ci){
        this.ci=ci;
    }

    public int identification(){
        System.out.println("Veuillez entrer votre login:");
        String login = sc.nextLine();
        System.out.println("Veuillez entrer votre mot de passe:");
        String mdp = sc.nextLine();
        return this.ci.sidentifier(login,mdp);
    }
}
