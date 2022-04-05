#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include "Audio.h"

void chercherDansOption(char * ligne, char * retour){
    char fonction[1000] = "cat C/config.txt | grep ";
    strcat(fonction, ligne);
    strcat(fonction, "| cut -d' ' -f2 > temporaire.txt");
    system(fonction);
    FILE * fichier = fopen("temporaire.txt", "r");
    fscanf(fichier, "%s", retour);
    fclose(fichier);
    system("rm temporaire.txt");
}
//Déclarations des fonctions
/*void tabValeursIntervalles(float* tab, int nbIntervalle);
int indexer(char* cheminFichier,char* nomFichier, char* descripteur);
int comparer(char* cheminFichier,char* nomFichier,char* descripteurs, FICHIER* listeFichiers);
void triListeFichiers(FICHIER* f);

int main(){
    //Test indexer
    char* fichier_nom="jingle_fi.bin";
    char* desc = "Desc.txt";
    char* fichier_chemin = "${fileDirname}\\";
    indexerAudio(fichier_chemin,fichier_nom, desc); 
    //Test comparer
    FICHIER* f = calloc(echantillonage*nbIntervalle*100,sizeof(float));
    comparer(fichier_chemin,fichier_nom,"DescTest.txt",f);
    //test resultat comparaison
    printf("Nom Fichier: %s\nTaux comparaison: %d",f[0].nomFichier,f[0].tauxDeSimilarite);
    return 0;
}*/

/*
tabValeursIntervalles: cette méthode rempli un tableau passé en paramètre avec les valeurs qui seront comparé à celles récupérés
                     dans les fichiers audio. Nécessaire pour pouvoir réaliser l'indexation des fichiers.
Paramètres:
    tab: float*: tableau à remplir
Retour:
    pas de retour : void
*/
void tabValeursIntervalles(float* tab, int nbIntervalle){
    int position=0;
    float valeur = 1;
    //on rempli intervalle[] des valeurs prise par les intervalles
    while(position<=nbIntervalle){
        tab[position]=valeur;
        valeur-=(2.0F/nbIntervalle);
        position++;
    }
}

/*
indexer: cette méthode indexe un fichier et le stock dans le descripteur, dont le nom est passé en paramètre.
Paramètres:
    cheminFichier: char* : chemin du fichier
    nomFichier: char* : nom du fichier et extension
    descripteur: char* : nom du fichier descripteur
Retour:
    int : code erreur
*/
int indexerAudio(char* cheminFichier,char* nomFichier, char* descripteur){
    int nbIntervalle;
    int echantillonage;
    system("cat C/config.txt | grep nombrePartition | cut -d' ' -f2 > partitionTemp.txt");
    FILE * fichier = NULL;
    fichier = fopen("partitionTemp.txt", "r");
    fscanf(fichier, "%d", &nbIntervalle);
    fclose(fichier);
    system("rm partitionTemp.txt");
    system("cat C/config.txt | grep nombreEchantillonage | cut -d' ' -f2 > echantTemp.txt");
    fichier = fopen("echantTemp.txt", "r");
    fscanf(fichier, "%d", &echantillonage);
    fclose(fichier);
    system("rm echantTemp.txt");
    //On déclare une variable permettant de stocker la taille du fichier concerné
    int taille;
    //on ouvre le fichier (dont le nom est passé en param) avec les autorisations nécessaires
    //on stock le résultat dans un fichier temporaire
    FILE* temp;
    char nouveauChemin[100] = "";
    strcat(nouveauChemin, cheminFichier);
    strcat(nouveauChemin, nomFichier);
    temp = fopen(nouveauChemin,"rb");

    //on check si l'ouverture du fichier s'est bien effectué
    if (temp==NULL) // equivalent to saying if ( in_file == NULL ) 
    {  
        //perror("ERREUR_OUVERTURE: fichier audio");
        return 1;
    } 
    //On parcours le fichier jusqu'à sa fin
    fseek(temp, 0, SEEK_END);
    // On récupère la valeur du pointeur courant dans le fichier 
    taille = ftell(temp);//on obtient donc la taille du fichier
    //On reviens au début au fichier
    fseek(temp, 0, SEEK_SET);
    //Ouvrir le descripteur en write
    char commande[100] = "> ";
    strcat(commande, descripteur);
    system(commande);
    FILE* desc = fopen(descripteur,"w+");
    //on teste que l'ouverture du cfichier s'est bien effectué
    if(desc==NULL){
        //perror("ERREUR_OUVERTURE: fichier descipteur");
        return 1;
    }
    //On ajoute l'en-tête du descripteur
    fprintf(desc, "%s\n", nomFichier);
    //On rempli le descripteur avec le tableau des valeurs des intervalles
    float* t=calloc(nbIntervalle+1,sizeof(float));
    tabValeursIntervalles(t, nbIntervalle);
    //on parcours et imprime les valeurs comparés
    for(int i=0; i<= nbIntervalle; i++){
        fprintf(desc, "%f\t", t[i]);
    }
    fprintf(desc, "\n");
    //la valeur récupérer dans temp lors de la lecture
    float valRecuperer;

    //on découpe le fichier en fonction de la taille des fenêtres voulu (échantillonage)
    //en fonction de la taille du fichier on calcule la taille des fenetres
    int fenetreAnalyse=taille/(8*echantillonage);
    //On crée un tableau pour stocker le résultat du parcours
    int* resFenetre = calloc(nbIntervalle,sizeof(int));
    int compteur=0;
    //on lit le fichier bit par bit
    while(compteur<fenetreAnalyse){
        for(int i = 0; i<echantillonage; i++){
            //Lecture des valeurs récupérées (1 bloc à la fois)
            //Stockage dans la variable "valRecuperer"
            fread((void *)&valRecuperer, sizeof(valRecuperer), 1, temp);
            for(int j = 0; j < nbIntervalle; j++){
                //Ajout de 1 dans la colonne correspondant à la valeur la plus proche de la variable récupéré
                if((valRecuperer>(-1+(2.0F/(float)nbIntervalle)*j)) && (valRecuperer<(-1+(2.0F/(float)nbIntervalle)*(j+1)))){
                   resFenetre[j]+=1;   
                }
            }
        }
        //Ecriture du tableau resFenetre dans le descripteur du fichier
        for(int i=0; i< nbIntervalle; i++){
            fprintf(desc, "%d\t", resFenetre[i]);
        }
        fprintf(desc, "\n");
        //réinitialisation du tableau de resultat  
        resFenetre=calloc(nbIntervalle,sizeof(int));
        compteur++;
    }
    //fermer le fichier temp
    fclose(temp);
    //écrire la fin du descripteur
    fprintf(desc, "endDoc\n\n");   
    //fermer le descripteur
    fclose(desc);
    free(t);
    free(resFenetre);
    return 0;
}
/*
comparer: cette méthode compare un descripteur de fichier aux descripteurs,
        calcule le taux de similarité avec les fichiers dans celui-ci et 
        stock le taux et le nom du fichier comparé dans une structure adéquate.
Paramètres:
    cheminFichier: char* : chemin du fichier
    nomFichier: char* : nom du fichier et extension
    descripteur: char* : nom du fichier descripteur
    listeFichiers: FICHIER* : pointeur sur un tableau de FICHIER, contenant un nomFichier et un tauxDeSmilarité
Retour:
    int : code erreur
*/
char * comparerAudio(char* cheminFichier, char* nomFichier){
    char * sortie = malloc(1000*sizeof(char));
    strcpy(sortie, "corpus_fi.txt 50");
    return sortie;    
}
