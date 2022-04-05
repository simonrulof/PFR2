#ifndef INDEXATIONTEXTE
#define INDEXATIONTEXTE
#define MAX 1000

typedef struct st_FICHIER{
	char nomFichier[100];
	int tauxDeSimilarite;
} FICHIER;

typedef struct stWord{           //on cree une structure qui stockera un mot et le nb de fois ou il apparait
    char mot[MAX];
    int occ;
}word;

int indexerTxtSeul(char * dossierFichier, char * nomFichierAIndexer, char * nomFichierStockage);

int comparateurString(char * string1, char * string2);

char * comparerDescripteursTxt(char ** motsCles, int i);

char * comparerDescripteursTxtDoc(char * dossierFichierCherche, char * NomFichierAChercher);

#endif
