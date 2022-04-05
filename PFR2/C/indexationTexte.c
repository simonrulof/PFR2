#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "indexationTexte.h"

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

//renvoie 1 si identique, 0 sinon
int comparateurString(char * string1, char * string2){

    int i, j;
    for(i = 0; string1[i] != '\n' && string1[i] != '\r' && string1[i] != '\0'; i++);
    for(j = 0; string2[j] != '\0'; j++);
    if (i != j) return 0;
    for(i = 0; i < j; i++){
        if (string1[i] != string2[i]) return 0;
    }
    return 1;
}

//permet de reduire une chaine de caractere 
void decalage(char * truc){
    
    int i = 0;
    while (truc[i+1] != '\0'){
        truc[i] = truc[i+1];
        i++;
    }
    truc[i] = truc[i+1];
}

//permet de reduire une chaine de caractere 
void decalageInverse(char * truc){ 
    
    int i = 0;
    while(truc[i] != '\0'){
        i++;
    }
    truc[i-1] = '\0';
}

//permet le nettoyage d'une chaine de caractere afin de ne recuperer que des lettres
void clean(char * truc){
    
    char trucAClean[MAX] = ":;.,?!'()";
    for(int i = 0; i < strlen(trucAClean); i++){
        for(int j = 0; j < strlen(truc); j++){
            if (truc[j] == trucAClean[i]) {
                if (trucAClean[i] == '\''){
                    while (truc[0] != '\''){
                        for(int k = 0; k < strlen(truc); k++) truc[k] = truc[k+1];
                    }
                    for(int k = 0; k < strlen(truc); k++) truc[k] = truc[k+1];
                }
                else for (int k = j; k < strlen(truc); k++) truc[k] = truc[k+1];
            }
        }
    }
}

//verifie si le mot est interessant a conserver (par le biais de la stoplist), renvoi 1 si interessant, 0 sinon et -1 s'il y a une erreure
int isInteressant(char * truc){
    
    if (!truc || truc[0] == '\0' || truc[0] == ' ') return 0;

    //on compare notre mot truc a la stoplist a l'aide d'un grep
    char commande[100] = "cat C/stoplist.txt | grep ";
    strcat(commande, truc);
    strcat(commande, " > temporaire.txt");
    system(commande);

    //ouverture du fichier dans lequel est stockee dans le resultat du grep
    FILE * temporaire = NULL;
    temporaire = fopen("temporaire.txt", "r");
    if (!temporaire) return -1;
    char temp[30];

    //boucle de lecture du fichier et comparaison
    while(!feof(temporaire)){
        fscanf(temporaire,"%s",temp);
        if (!strcmp(temp, truc)){
            return 0;
        }
    }
    fclose(temporaire);
    system("rm temporaire.txt");
    return 1;
}

//un tri pas tres joli
void triMocheWord(word * wordList, int nombreMots){
    
    for(int i = 0; i < nombreMots; i++){
        for(int j = i+1; j < nombreMots; j++){
            if(j != i && wordList[i].occ < wordList[j].occ){
                word temp = wordList[i];
                wordList[i] = wordList[j];
                wordList[j] = temp;
            }
        }
    }


}

//permet d'editer le descripteur d'un document, retourne 0 si l'operation a ete effectue, 1 sinon
int construireLeDescripteur(word * wordList,int nombreMots, char * nomFichierStockage, char * nomFichierAIndexer){

    char commande[100] = "> ";
    strcat(commande, nomFichierStockage);
    system(commande);
    FILE * fichier = fopen(nomFichierStockage, "r+");
    if(!fichier) return 1;
    system("cat C/config.txt | grep nombreMotCles | cut -d' ' -f2 > temporaire.txt");

    //ouverture du fichier et lecture du resultat
    FILE * temporaire = NULL;
    temporaire = fopen("temporaire.txt", "r");
    if (!temporaire) return 1;
    int nbreMax;
    fscanf(temporaire, "%d", &nbreMax);
    fclose(temporaire);
    system("rm temporaire.txt");
    fprintf(fichier, "%s\n", nomFichierAIndexer);

    //ecriture dans le fichier
    for(int i = 0; i < nombreMots && i < nbreMax; i++){
        fprintf(fichier, "%s %d\n", wordList[i].mot, wordList[i].occ);
    }
    fprintf(fichier, "endDoc\n\n");
    fclose(fichier);
    return 0;
}

//enleve les balises d'un document xml ainsi que les accents contenues dans son texte
void enleverBalise(char * nouveau, char * cheminFichier){

    FILE * temp = NULL;
    system("> texteTemporaire.xml");
    temp = fopen("texteTemporaire.xml", "w");
    FILE * fichier = NULL;
    fichier = fopen(cheminFichier, "r");
    char lettre;

    //on parcours le fichier et copie tout ce qui n'est pas une balise dans un nouveau fichier
    while(!feof(fichier)){
        lettre = fgetc(fichier);
        if (lettre == '<'){
            while (lettre != '>'){
                lettre = fgetc(fichier);
            }
        }else{
            if (lettre == '-') lettre = ' ';
            fprintf(temp, "%c", lettre);
        }
    }
    fclose(temp);
    fclose(fichier);

    //retire les accents via une commande unix iconv
    char commande[1000] = "cat texteTemporaire.xml | iconv -f ISO88591 -t ASCII//TRANSLIT > ";
    strcat(commande, nouveau);
    system(commande);
    system("rm texteTemporaire.xml");
}

//indexe un fichier xml seul et cree son descripteur apres avoir nettoyer le texte
int indexerTxtSeul(char * dossierFichier, char * nomFichierAIndexer, char * nomFichierStockage){

    //recupere le fichier a indexer et le copie dans le dossier des fichiers indexers
    char commande[100] = "cp ";
    strcat(commande, dossierFichier);
    strcat(commande, nomFichierAIndexer);
    strcat(commande, " fichierIndexTemp.txt");
    char cheminFichier[100] = "";
    strcat(cheminFichier, dossierFichier);
    strcat(cheminFichier, nomFichierAIndexer);

    //on nettoie le fichier et on cree un descripteur temporaire
    enleverBalise("fichierIndexTemp.txt", cheminFichier);
    word wordList[MAX];
    int nombreMots = 0;
    FILE * fichier = fopen("fichierIndexTemp.txt","r");             
    if (fichier == NULL){                                       
        return 1;                                               
    }

    //on parcours le fichier, on recupere les mots, les nettoie, et verifie s'ils sont interessants
    char temp[MAX];
    while(!feof(fichier)){
        fscanf(fichier,"%s",temp);
        clean(temp);
        int estInteressant;
        estInteressant = isInteressant(temp);
        if (estInteressant == -1) return 1;
        if (estInteressant != 0){

        //si le mot est deja present on incremente occ sinon on l'ajoute a notre liste de mots
        int ajouter = 1;
            for(int i = 0; i < nombreMots; i++){
                if (!strcmp(wordList[i].mot, temp)){
                    wordList[i].occ++;
                    ajouter = 0;
                    break;
                }
                ajouter = 1;
            }
            if (ajouter){
                memcpy(wordList[nombreMots].mot, temp, 1000);
                wordList[nombreMots].occ = 1;
                nombreMots++;
            }
        }
    }

    //on trie la liste et on cree le descripteurs qu'on ajoute a la liste des descripteurs
    triMocheWord(wordList, nombreMots);
    int check = construireLeDescripteur(wordList, nombreMots, nomFichierStockage, nomFichierAIndexer);
    if(check) return check;
    fclose(fichier);
    system("rm fichierIndexTemp.txt");
    return 0;
}

//recupere un mot d'une chaine de caractere, destinee a recuperer un mot cle d'un descripteur
void getMot(char * ligne, char * mot){

    int i = 0;
    while (ligne[i] != ' '){
        mot[i] = ligne[i];
        i++;
    }mot[i] = '\0';
}

//recupere un chiffre dans une chaine de caractere, destinee a recuperer le nombre d'occurence dans un descripteur
int getNombre(char * ligne){

    int i = 0;
    char mot[100];
    while (ligne[i] != ' '){
        i++;
    }i++;
    int j = 0;
    while(ligne[i+j] != '\0'){
        mot[j] = ligne[i+j];
        j++;
    }
    return atoi(mot);

}

//trie le fichier contenant les descripteurs apres la comparaison
void triMocheFichier(FICHIER * listeFichier, int nbreFichier){

    for(int i = 0; i < nbreFichier; i++){
        for(int j = i+1; j < nbreFichier; j++){
            if(j != i && listeFichier[i].tauxDeSimilarite < listeFichier[j].tauxDeSimilarite){
                FICHIER temp = listeFichier[i];
                listeFichier[i] = listeFichier[j];
                listeFichier[j] = temp;
            }
        }
    }
}

//compare une chaine de mots cles a notre base de descripteurs
char * comparerDescripteursTxt(char ** motsCles, int i){
    char cheminDossier[100];
    chercherDansOption("cheminDescripteurs", cheminDossier);
    char nomDescripteur[100];
    chercherDansOption("nomDescripteurTexte", nomDescripteur);
    char fichierDescripteur[100];
    strcat(fichierDescripteur, "C/");
    strcat(fichierDescripteur, cheminDossier);
    strcat(fichierDescripteur, nomDescripteur);

    FILE * descripteur = NULL;
    char ligne[100];
    descripteur = fopen(fichierDescripteur, "r");
    int j = 0;
    FICHIER listeFichiers[100];

    //on lit les descripteurs un par un dans notre liste de descripteurs
    fgets(ligne, 1000, descripteur);
    fgets(ligne, 1000, descripteur);
    while(!comparateurString(ligne, "END_DESC")){
        strcpy(listeFichiers[j].nomFichier, ligne);
        listeFichiers[j].tauxDeSimilarite = 0;
        fgets(ligne, 1000, descripteur);

        //on compare nos mots cles aux descripteurs et on actualise le taux de similarite de chaque descripteurs
        while(!comparateurString(ligne, "endDoc") && !comparateurString(ligne, "END_DESC")){
            char truc[100];
            int nombre;
            getMot(ligne, truc);
            nombre = getNombre(ligne);
            for(int k = 0; k < i; k++){
                if (strcmp(motsCles[k], truc)==0){
                    listeFichiers[j].tauxDeSimilarite += nombre;
                }
            }
            fgets(ligne, 1000, descripteur);
        }
        fgets(ligne, 1000, descripteur);
        j++;
    }

    //on trie la liste de resultat et on ferme la liste de descripteurs
    triMocheFichier(listeFichiers, i);
    fclose(descripteur);
    
    char cheminTexte[100];
    chercherDansOption("cheminTexte", cheminTexte);
    char * tableauStringFichier = malloc(1000*sizeof(char));
    for(int k = 0; k < j; k++){
        strcat(tableauStringFichier, "C/");
        strcat(tableauStringFichier, cheminTexte);
        strcat(tableauStringFichier, listeFichiers[k].nomFichier);
        char nombre[10];
        sprintf(nombre, "%d", listeFichiers[k].tauxDeSimilarite);
        int a = strlen(tableauStringFichier);
        tableauStringFichier[a-1] = '\0';
        strcat(tableauStringFichier, " ");
        strcat(tableauStringFichier, nombre);
        strcat(tableauStringFichier, " ");
    } 
    return tableauStringFichier;
}

//compare un documement, qui sera alors indexer, au reste de la ligne de descripteurs
char * comparerDescripteursTxtDoc(char * dossierFichierCherche, char * NomFichierAChercher){
    char cheminDossier[100];
    chercherDansOption("cheminDescripteurs", cheminDossier);
    char nomDescripteur[100];
    chercherDansOption("nomDescripteurTexte", nomDescripteur);
    char fichierDescripteur[1000];
    strcpy(fichierDescripteur, "C/");
    strcat(fichierDescripteur, cheminDossier);
    strcat(fichierDescripteur, nomDescripteur);

    //on indexe le document a comparer
    indexerTxtSeul(dossierFichierCherche, NomFichierAChercher, "fichierTemporaire.txt");
    FILE * descripteur = NULL;
    descripteur = fopen("fichierTemporaire.txt","r");
    char * mot[100];
    int occ[100];
    char ligne[1000];
    int i = 0;
    fgets(ligne, 1000, descripteur);
    fgets(ligne, 1000, descripteur);
    //on parcours le descripteur en stockant ses mots et ses occs
    while(!comparateurString(ligne, "endDoc")){
        mot[i] = malloc(100*sizeof(char));
        getMot(ligne,mot[i]);
        occ[i] = getNombre(ligne);
        i++;
        fgets(ligne, 1000, descripteur);
    }  
    fclose(descripteur);
    system("rm fichierTemporaire.txt");
    //on parcours la liste des descripteurs
    descripteur = fopen(fichierDescripteur, "r");
    if (!descripteur) printf("mais nique\n");
    fflush(stdout);
    int j = 0;
    FICHIER listeFichiers[100];
    fgets(ligne, 1000, descripteur);
    fgets(ligne, 1000, descripteur);
    while(!comparateurString(ligne, "END_DESC")){
        strcpy(listeFichiers[j].nomFichier, ligne);
        listeFichiers[j].tauxDeSimilarite = 0;
        fgets(ligne, 1000, descripteur);

        //on recupere leur mots et occs et on les compare avec ceux du fichier a comparer
        while(!comparateurString(ligne, "endDoc") && !comparateurString(ligne, "END_DESC")){
            char truc[100];
            int nombre;
            getMot(ligne, truc);
            nombre = getNombre(ligne);
            for(int k = 0; k < i; k++){
                if (strcmp(mot[k], truc)==0){
                    listeFichiers[j].tauxDeSimilarite += nombre*occ[j];
                }
            }
            fgets(ligne, 1000, descripteur);
        }
        fgets(ligne, 1000, descripteur);
        j++;
    }

    // on trie la liste de resultat
    triMocheFichier(listeFichiers, j);
    fclose(descripteur);
    char * tableauStringFichier = malloc(1000*sizeof(char));
    strcpy(tableauStringFichier, "");
    for(int k = 0; k < j; k++){
        strcat(tableauStringFichier, listeFichiers[k].nomFichier);
        char nombre[10];
        sprintf(nombre, "%d", listeFichiers[k].tauxDeSimilarite);
        int a = strlen(tableauStringFichier);
        tableauStringFichier[a-1] = '\0';
        strcat(tableauStringFichier, " ");
        strcat(tableauStringFichier, nombre);
        strcat(tableauStringFichier, " ");
    } 
    return tableauStringFichier;
}
