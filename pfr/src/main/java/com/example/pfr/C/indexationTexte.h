#ifndef INDEXATIONTEXTE
#define INDEXATIONTEXTE
#include "struct.h"
#define MAX 1000

typedef struct stWord{           //on cree une structure qui stockera un mot et le nb de fois ou il apparait
    char mot[MAX];
    int occ;
}word;

int indexerTxtSeul(char * dossierFichier, char * nomFichierAIndexer, char * nomFichierStockage);

int comparateurString(char * string1, char * string2);

int comparerDescripteursTxt(char ** motsCles, int i, char * fichierDescripteur, FICHIER * listeFichiers);

int comparerDescripteursTxtDoc(char * dossierFichierCherche, char * NomFichierAChercher, char * fichierDescripteur, FICHIER * listeFichiers);

#endif