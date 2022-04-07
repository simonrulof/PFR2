#ifndef FONCTIONADMIN
#define FONCTIONADMIN
#include "struct.h"

int lireDescripteur(char * nomDescripteur);

int ajouterUnFichier(char * cheminFichier, char * nomFichier, char * cheminDossierFichiers, char * fichierDescripteur, char * typeFichier);

int supprFichier(char * CheminFichier, char * nomFichier, char * cheminDescripteur, char * fichierDescripteur, char * typeFichier);

int refaireUnDescripteur(char * dossierFichier, char * nomFichier, char * fichierDescripteur, char * typeFichier);

int reindexerTousLesFichiers(char * nomFichierDescripteur, char * dossierFichier, char * type);

#endif