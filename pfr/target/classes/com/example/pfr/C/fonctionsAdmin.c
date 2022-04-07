#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fonctionsAdmin.h"
#include "struct.h"
#include "indexationTexte.h"
#include "IndexImage.h"
#include "Audio.h"

int enleverUnDescripteur(char * fichierDescripteur, char * nomFichier){
    //enelve le descripteur de nomFichier dans le descripteur si il existe
    //renvoie 0 si tout s'est bien pasé, 1 sinon
    system("> nouveauDescripteur.txt");

    FILE * descripteur = NULL;
    descripteur = fopen(fichierDescripteur, "r+");
    if (!descripteur){
        return 1;
    }

    FILE * nouveauDescripteur = NULL;
    nouveauDescripteur = fopen("nouveauDescripteur.txt", "r+");
    if (!nouveauDescripteur){
        fclose(descripteur);
        return 1;
    }

    char * ligneDescripteur = (char *) malloc(1000*sizeof(char));
    char test[1000];

    ligneDescripteur = fgets(test, 1000, descripteur);
    
    while (!comparateurString(ligneDescripteur, nomFichier)){
        fprintf(nouveauDescripteur, "%s", ligneDescripteur);
        ligneDescripteur = fgets(test, 1000, descripteur); 
        if (comparateurString(ligneDescripteur, "END_DESC")){
            fclose(nouveauDescripteur); 
            fclose(descripteur);
            return 0;
        }
    }
    while(!comparateurString(ligneDescripteur, "endDoc")){
        ligneDescripteur = fgets(test, 1000, descripteur);
    }
    while(!comparateurString(ligneDescripteur, "END_DESC")){
        ligneDescripteur = fgets(test, 1000, descripteur); 
        fprintf(nouveauDescripteur, "%s", ligneDescripteur);
    }

    fclose(descripteur);
    fclose(nouveauDescripteur);
    char commande[100] = "rm \0";
    strcat(commande, fichierDescripteur);
    system(commande);
    rename("nouveauDescripteur.txt", fichierDescripteur);
    return 0;

}

int ajouterUnDescripteur(char * fichierDescripteur, char * temp){
    char * ligneDescripteur = (char *) malloc(1000*sizeof(char));
    char test[1000];
    FILE * descripteur = NULL;
    descripteur = fopen(fichierDescripteur, "r+");
    if(!descripteur) {
        return 1;
    }
    
    int i =0;
    ligneDescripteur = fgets(test, 1000, descripteur);
    while(!comparateurString(ligneDescripteur, "END_DESC")){
        i++;
        ligneDescripteur = fgets(test, 1000, descripteur);
    }

    rewind(descripteur);

    for(int j = 0; j < i; j++){
        ligneDescripteur = fgets(test, 1000, descripteur);
    }
    
    FILE * descripteurTemp = NULL;
    descripteurTemp = fopen(temp, "r+");
    if (!descripteurTemp){
        fclose(descripteur);
        return 1;
    }  
    ligneDescripteur = fgets(test, 1000, descripteurTemp);
    fprintf(descripteur, "%s", ligneDescripteur);
    while(!comparateurString(ligneDescripteur, "endDoc")){
        ligneDescripteur = fgets(test, 1000, descripteurTemp);
        fprintf(descripteur, "%s", ligneDescripteur);
    }
    fprintf(descripteur, "END_DESC\n");
    fclose(descripteurTemp);
    fflush(descripteur);
    return 0;
}

int refaireUnDescripteur(char * dossierFichier, char * nomFichier, char * fichierDescripteur, char * typeFichier){
    //nomFichier : nom du fichier à indexer ou réindexer
    //fichierDescripteur : chemin vers le descripteur
    //typeFichier : doit etre : "Son", "Img", ou "Txt"
    //renvoie 0 si réussi, 1 sinon

    if (comparateurString(typeFichier, "Txt")){
        indexerTxtSeul(dossierFichier, nomFichier, "descripteurTemp.txt");
    }
    else if (comparateurString(typeFichier, "Img")){
        indexationImage(dossierFichier, nomFichier, "descripteurTemp.txt");
    }
    else if (comparateurString(typeFichier, "Son")){
        indexerAudio(dossierFichier , nomFichier, "descripteurTemp.txt");
    }
    else{
        return 1;
    }

    int check = enleverUnDescripteur(fichierDescripteur, nomFichier);
    if (check != 0) return check;

    check = ajouterUnDescripteur(fichierDescripteur, "descripteurTemp.txt");
    if (check != 0) return check;

    system("rm descripteurTemp.txt");


    return 0;
}



int lireDescripteur(char * nomDescripteur){
    //renvoie 0 si lecture réussie, 1 sinon
    FILE *fichier = NULL;
    fichier = fopen(nomDescripteur, "r");
    if (!fichier) return 1;


    char scan;
    scanf("%c", &scan);
    char *ligne = (char *) malloc(1000*sizeof(char));
    char test[1000];
    ligne = fgets(test, 1000, fichier);
    if (!comparateurString(ligne, "DEBUT_DESC")) {
        fclose(fichier); 
        return 1;
    }
    ligne = fgets(test, 1000, fichier);
    while (!comparateurString(ligne, "END_DESC")){
        system("clear");
        while(!comparateurString(ligne, "endDoc") && !comparateurString(ligne, "END_DESC")) {
            printf("%s", ligne);
            ligne = fgets(test, 1000, fichier);
        }
        ligne = fgets(test, 1000, fichier);
        printf("APPUYEZ SUR ENTRER POUR CONTINUER\n");
        scanf("%c", &scan);
    }
    system("clear");
    fclose(fichier);
    return 0;
}


// Image NB (.bmp)  : return 0
// Image RGB (.jpg) : return 1
// Erreur           : return -1
int getTypeImageNC(char * cheminFichier){

    FILE* fichier = NULL;
    fichier = fopen(cheminFichier, "r");
    if (!fichier) return -2;
    //Lecture de la taille de l'image
    int x=0, y=0 ,z=0;
    fscanf(fichier, "%d %d %d", &x, &y, &z);
    fclose(fichier);
    
    if(z == 1){
        return 0; 
    }

    if(z == 3){
        return 1;
    }

    return -1;
}

int supprFichier(char * CheminFichier, char * nomFichier, char * cheminDescripteur, char * fichierDescripteur, char * typeFichier){
//chemin doit aussi contenir le nom du fichier (ex : Dossier/nomFichier)
//renvoie 0 si le fichier a bien été supprimé, renvoie 0 sinon
//les droits du fichier doivent etre vérifié avant

    strcat(CheminFichier, nomFichier);
    char suppr[100] = "rm \0";
    strcat(suppr, CheminFichier);
    strcat(cheminDescripteur, fichierDescripteur);
    if (strcmp(typeFichier, "Img")==0){
        int i = 0;
        char nouveauChemin[100] = "";
        for(i = 0; CheminFichier[i]!= '.'; i++) nouveauChemin[i] = CheminFichier[i];
        int a = getTypeImageNC(CheminFichier);
        if (a == 0){
            strcat(nouveauChemin, ".bmp");
        }
        else if (a == 1){
            strcat(nouveauChemin, ".jpg");
        }
        char commande[100] = "rm \0";
        strcat(commande, nouveauChemin);
        system(commande);
    }if (strcmp(typeFichier, "Son") == 0){
        int i = 0;
        char nouveauChemin1[100] = "";
        for(i = 0; CheminFichier[i]!= '.'; i++) nouveauChemin1[i] = CheminFichier[i];
        char nouveauChemin2[100] = "";
        strcpy(nouveauChemin2, nouveauChemin1);
        strcat(nouveauChemin1, ".wav");
        strcat(nouveauChemin2, ".txt");
        
        char commande1[100] = "rm \0";
        char commande2[100] = "rm \0";
        strcat(commande1, nouveauChemin1);
        strcat(commande2, nouveauChemin2);
        system(commande1);
        system(commande2);
    }
    system(suppr);
    int check = enleverUnDescripteur(cheminDescripteur, nomFichier);
    return check;
}



int ajouterUnFichier(char * cheminFichier, char * nomFichier, char * cheminDossierFichiers, char * fichierDescripteur, char * typeFichier){
    
    
    char commande[1000] = "cp \0";
    strcat(commande, cheminFichier);
    strcat(commande, " ");
    strcat(commande, cheminDossierFichiers);
    system(commande);
    if (strcmp(typeFichier, "Img") == 0){
        int a  = getTypeImageNC(cheminFichier);
        int i=0;
        char autreFichier[100] = "";
        for(i = 0; cheminFichier[i] != '.'; i++) autreFichier[i] = cheminFichier[i];
        if (a == 0){
            strcat(autreFichier, ".bmp");
        }
        else if (a == 1){
            strcat(autreFichier, ".jpg");
        }
        char commande1[1000] = "cp \0";
        strcat(commande1, autreFichier);
        strcat(commande1, " ");
        strcat(commande1, cheminDossierFichiers);
        system(commande1);
    }if (strcmp(typeFichier, "Son") == 0){
        int i = 0;
        char autreFichier1[1000] = "";
        for(i = 0; cheminFichier[i] != '.'; i++) autreFichier1[i] = cheminFichier[i];
        char autreFichier2[1000] = "";
        strcpy(autreFichier2, autreFichier1);
        strcat(autreFichier1, ".txt");
        strcat(autreFichier2, ".wav");
        char commande1[1000] = "cp \0";
        char commande2[1000] = "cp \0";
        strcat(commande1, autreFichier1);
        strcat(commande2, autreFichier2);
        strcat(commande1, " ");
        strcat(commande2, " ");
        strcat(commande1, cheminDossierFichiers);
        strcat(commande2, cheminDossierFichiers);
        system(commande1);
        system(commande2);
    }
    
    int check = refaireUnDescripteur(cheminDossierFichiers, nomFichier, fichierDescripteur, typeFichier);
    return check;
}

int reindexerTousLesFichiers(char * nomFichierDescripteur, char * dossierFichier, char * type){
    char commande[100] = "rm \0";
    strcat(commande, nomFichierDescripteur);
    system(commande);
    char commande2[100] = "> \0";
    strcat(commande2, nomFichierDescripteur);
    system(commande2);
    FILE * nouveauDescripteur = NULL;   
    nouveauDescripteur = fopen(nomFichierDescripteur, "r+");
    if (nouveauDescripteur == NULL) return 1;

    fprintf(nouveauDescripteur, "DEBUT_DESC\nEND_DESC\n\n");
    fclose(nouveauDescripteur);

    char commande3[100] = "ls \0";
    strcat(commande3, dossierFichier);
    if (strcmp(type, "Txt") == 0){
        strcat(commande3, " | grep .xml");
    }
    if (strcmp(type, "Img") == 0){
        strcat(commande3, " | grep .txt");
    }
    if (strcmp(type, "Son") == 0){
        strcat(commande3, " | grep .bin");
    }
    strcat(commande3, " > temporairereindex.txt");
    system(commande3);
    FILE * temporaire = NULL;
    temporaire = fopen("temporairereindex.txt", "r");
    if (temporaire == NULL) return 1;
    char * ligne = malloc(100*sizeof(char));
    fscanf(temporaire, "%s", ligne);
    while (!feof(temporaire)){
        if (strcmp(type, "Txt") == 0){
            indexerTxtSeul(dossierFichier, ligne, "descripteurTemp.txt");
        }
        if (strcmp(type, "Img") == 0){
            indexationImage(dossierFichier, ligne, "descripteurTemp.txt");
        }
        if (strcmp(type, "Son") == 0){
            indexerAudio(dossierFichier, ligne, "descripteurTemp.txt");
        }
        ajouterUnDescripteur(nomFichierDescripteur, "descripteurTemp.txt");
        system("rm descripteurTemp.txt");

        fscanf(temporaire, "%s", ligne);
    }

    fclose(temporaire);
    system("rm temporairereindex.txt");

    
}

/*int main( int argc, char ** argv){
    //if (argc != 2) return 0;
    //supprFichier(argv[1], "anna.txt", "descripteurTexte.txt");
    //lireDescripteur("descripteurTexte.txt");
    //refaireUnDescripteur("anna.txt", "descripteurTexte.txt", "Txt");
    ajouterUnFichier("mehdi.txt", "mehdi.txt", "DocTexte/", "descripteurTexte.txt", "Txt");
    //reindexerTousLesFichiers("Descripteurs/descripteurTexte.txt", "DocTexte/", "Txt");
    return 0;
}*/